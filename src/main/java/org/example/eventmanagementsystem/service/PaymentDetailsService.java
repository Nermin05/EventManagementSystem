package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.encyript.EncryptionUtil;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.model.PaymentDetails;
import org.example.eventmanagementsystem.repository.PaymentDetailsRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetails saveDetails(PaymentDetails paymentDetails) throws Exception {
        paymentDetails.setCardNumber(EncryptionUtil.encrypt(paymentDetails.getCardNumber()));
        paymentDetails.setCvv(EncryptionUtil.encrypt(paymentDetails.getCvv()));
        paymentDetails.setExpirationDate(EncryptionUtil.encrypt(paymentDetails.getExpirationDate()));
        return paymentDetailsRepository.save(paymentDetails);
    }

    public PaymentDetails getDetails(Long id) throws Exception {
        PaymentDetails paymentDetails = paymentDetailsRepository.findById(id).orElseThrow(() -> {
            log.error("Payment details can not found");
            return new ResourceNotFoundException("Payment details can not found");
        });
        paymentDetails.setCardNumber(EncryptionUtil.decrypt(paymentDetails.getCardNumber()));
        paymentDetails.setCvv(EncryptionUtil.decrypt(paymentDetails.getCvv()));
        paymentDetails.setExpirationDate(EncryptionUtil.decrypt(paymentDetails.getExpirationDate()));
        return paymentDetails;
    }
}
