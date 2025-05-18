package org.example.eventmanagementsystem.dto.payment;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddPaymentDto(@NotNull Long userId, @NotNull Long ticketId,
                          @NotNull BigDecimal amount) {
}
