package org.example.eventmanagementsystem.dto.ticket;

import lombok.Data;

public record AddTicketDto(Long bookingId,Long eventId,
                           String ticketCode) {

}
