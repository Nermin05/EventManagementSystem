package org.example.eventmanagementsystem.dto.ticket;

public record UpdatedTicketDto(Long bookingId,Long eventId,
                               String ticketCode) {
}
