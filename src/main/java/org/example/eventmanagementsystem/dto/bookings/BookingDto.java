package org.example.eventmanagementsystem.dto.bookings;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingDto(Long userId,Long eventId,Integer numTickets,BigDecimal totalPrice,
     LocalDateTime bookingDate) {
}
