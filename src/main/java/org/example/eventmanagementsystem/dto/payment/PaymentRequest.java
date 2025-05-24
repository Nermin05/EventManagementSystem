package org.example.eventmanagementsystem.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(@NotNull Long userId, @NotNull Long ticketId,
                             @NotNull BigDecimal offeredPrice,
                             @NotBlank String cardNumber,
                             @NotBlank String cvv,
                             @NotBlank String expirationDate) {
}
