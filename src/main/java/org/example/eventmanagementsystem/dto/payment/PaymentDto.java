package org.example.eventmanagementsystem.dto.payment;

import org.example.eventmanagementsystem.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDto(Long userId, Long ticketId,
                         BigDecimal amount, PaymentStatus paymentStatus,
                         LocalDateTime paymentDate) {
}
