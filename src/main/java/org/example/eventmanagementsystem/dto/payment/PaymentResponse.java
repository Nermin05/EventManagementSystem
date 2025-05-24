package org.example.eventmanagementsystem.dto.payment;

import java.math.BigDecimal;

public record PaymentResponse(String message, BigDecimal amount) {
}
