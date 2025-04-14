package org.example.eventmanagementsystem.dto.bookings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatedBookingDto{
    private Long userId;
    private Long eventId;
    private Integer numTickets;
}
