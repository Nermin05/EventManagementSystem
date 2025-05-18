package org.example.eventmanagementsystem.dto.paymentdetails;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PaymentDetailsDto(@Size(min = 16, max = 16) String cardNumber,
                                @Size(min = 3, max = 3) String cvv,
                                @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}") String expirationDate,
                                BigDecimal balance, Long userId) {
}
