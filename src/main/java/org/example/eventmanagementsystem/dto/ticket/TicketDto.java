package org.example.eventmanagementsystem.dto.ticket;

import lombok.Data;
import org.example.eventmanagementsystem.model.enums.TicketStatus;

public record TicketDto(Long bookingId,Long eventId,
                        String ticketCode,TicketStatus status) {
}
