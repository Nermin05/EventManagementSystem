package org.example.eventmanagementsystem.dto.event;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EventDto(String name, String description,
                       LocalDate date, Long categoryId, Long venueId,
                       Long organizerId, BigDecimal price) {
}
