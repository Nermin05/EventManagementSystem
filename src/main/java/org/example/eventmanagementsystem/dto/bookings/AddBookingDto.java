package org.example.eventmanagementsystem.dto.bookings;

import jakarta.validation.constraints.NotNull;

public record AddBookingDto (@NotNull Long userId, @NotNull Long eventId,
                             @NotNull Integer numTickets) {
}
