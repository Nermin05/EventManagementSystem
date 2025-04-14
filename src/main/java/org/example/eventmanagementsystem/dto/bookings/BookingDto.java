package org.example.eventmanagementsystem.dto.bookings;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long userId;
    private Long eventId;
    private Integer numTickets;
    private BigDecimal totalPrice;
    private LocalDateTime bookingDate;
}
