package org.example.eventmanagementsystem.dto.event;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.eventmanagementsystem.dto.priceperiod.PricePeriodDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AddEventDto(@NotBlank String name, @NotBlank String description, @NotNull LocalDate date,
                          @NotNull Long categoryId, @NotNull Long venueId,
                          @NotNull Long organizerId, @NotNull BigDecimal price,
                          List<PricePeriodDto> pricePeriods) {
}
