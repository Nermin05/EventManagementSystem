package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.ticket.AddTicketDto;
import org.example.eventmanagementsystem.dto.ticket.TicketDto;
import org.example.eventmanagementsystem.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(source = "booking.id",target = "bookingId")
    @Mapping(source = "event.id",target = "eventId")
    TicketDto ticketToTicketDto(Ticket ticket);

    @Mapping(source = "booking.id",target = "bookingId")
    @Mapping(source = "event.id",target = "eventId")
    List<TicketDto> ticketsToTicketsDto(List<Ticket> tickets);

    @Mapping(source = "bookingId",target = "booking",qualifiedByName ="mapBookingFromId" )
    @Mapping(source = "eventId",target = "event",qualifiedByName ="mapEventFromId" )
    Ticket addTicketDtoToTicket(AddTicketDto addTicketDto);

    @Named("mapBookingFromId")
    default Booking mapBookingFromId(Long id) {
        if (id==null) return null;
        Booking booking=new Booking();
        booking.setId(id);
        return booking;
    }
    @Named("mapEventFromId")
    default Event mapEventFromId(Long id) {
        if (id==null) return null;
        Event event=new Event();
        event.setId(id);
        return event;
    }
}
