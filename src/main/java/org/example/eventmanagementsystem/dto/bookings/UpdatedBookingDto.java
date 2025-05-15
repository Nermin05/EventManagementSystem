package org.example.eventmanagementsystem.dto.bookings;


import jakarta.validation.constraints.NotNull;

public record UpdatedBookingDto(@NotNull Integer numTickets) {
}
