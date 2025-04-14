package org.example.eventmanagementsystem.dto.event;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class EventDto {
    private String name;
    private String description;
    private LocalDate date;
    private Long categoryId;
    private Long venueId;
    private Long organizerId;
    private BigDecimal price;
}
