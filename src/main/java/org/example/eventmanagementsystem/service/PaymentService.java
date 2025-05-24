package org.example.eventmanagementsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.payment.PaymentRequest;
import org.example.eventmanagementsystem.dto.payment.PaymentResponse;
import org.example.eventmanagementsystem.encyript.EncryptionUtil;
import org.example.eventmanagementsystem.exception.InsufficientBalanceException;
import org.example.eventmanagementsystem.exception.MismatchCardNumberException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.exception.TicketHaveAlreadySoldException;
import org.example.eventmanagementsystem.mapper.PaymentMapper;
import org.example.eventmanagementsystem.model.Payment;
import org.example.eventmanagementsystem.model.PaymentDetails;
import org.example.eventmanagementsystem.model.Ticket;
import org.example.eventmanagementsystem.model.User;
import org.example.eventmanagementsystem.model.enums.PaymentStatus;
import org.example.eventmanagementsystem.model.enums.TicketStatus;
import org.example.eventmanagementsystem.repository.PaymentDetailsRepository;
import org.example.eventmanagementsystem.repository.PaymentRepository;
import org.example.eventmanagementsystem.repository.TicketRepository;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponse makePayment(PaymentRequest paymentRequest) throws Exception {
        PaymentDetails paymentDetails = paymentDetailsRepository.findByUserId(paymentRequest.userId()).orElseThrow(() -> {
            log.error("User can not found");
            return new ResourceNotFoundException("User can not found");
        });
        String decryptedCardNumber = EncryptionUtil.decrypt(paymentDetails.getCardNumber());
        String decryptedCvv = EncryptionUtil.decrypt(paymentDetails.getCvv());
        String decryptedExpirationDate = EncryptionUtil.decrypt(paymentDetails.getExpirationDate());

        if (!decryptedCardNumber.equals(paymentRequest.cardNumber()) || !decryptedCvv.equals(paymentRequest.cvv()) || !decryptedExpirationDate.equals(paymentRequest.expirationDate())) {
            throw new MismatchCardNumberException("Mismatch card number");
        }
        Ticket ticket = ticketRepository.findById(paymentRequest.ticketId()).orElseThrow(() -> {
            log.error("Ticket can not found");
            return new ResourceNotFoundException("Ticket can not found");
        });
        if (ticket.getStatus() == TicketStatus.VALID) {
            throw new TicketHaveAlreadySoldException("Ticket have already sold");
        }
        if (paymentRequest.offeredPrice().compareTo(ticket.getMinPrice()) < 0 || paymentRequest.offeredPrice().compareTo(ticket.getMaxPrice()) > 0) {
            throw new IllegalArgumentException("Offered price is outside of allowed range");
        }
        if (paymentDetails.getBalance().compareTo(paymentRequest.offeredPrice()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        paymentDetails.setBalance(paymentDetails.getBalance().subtract(paymentRequest.offeredPrice()));
        User user = userRepository.findById(paymentRequest.userId()).orElseThrow(() -> {
            log.error("User can not found");
            return new ResourceNotFoundException("User can not found");
        });
        ticket.setStatus(TicketStatus.VALID);
        ticket.setUser(user);

        Payment payment = Payment.builder()
                .user(user)
                .ticket(ticket)
                .amount(paymentRequest.offeredPrice())
                .paymentDate(LocalDateTime.now())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();
        ticketRepository.save(ticket);
        paymentDetailsRepository.save(paymentDetails);
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.paymentToPaymenResponse(saved);
    }

}
