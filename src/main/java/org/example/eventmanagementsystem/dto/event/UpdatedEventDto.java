package org.example.eventmanagementsystem.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatedEventDto(@NotBlank String name, @NotBlank String description,
                              @NotNull LocalDate date, @NotNull Long categoryId,
                              @NotNull Long venueId, @NotNull Long organizerId,
                              @NotNull BigDecimal minPrice,@NotNull BigDecimal maxPrice) {
}
