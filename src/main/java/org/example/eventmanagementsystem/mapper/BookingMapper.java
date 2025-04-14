package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.bookings.AddBookingDto;
import org.example.eventmanagementsystem.dto.bookings.BookingDto;
import org.example.eventmanagementsystem.model.Booking;
import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "event.id",target = "eventId")
    BookingDto bookingToBookingDto(Booking booking);

    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "event.id",target = "eventId")
    List<BookingDto> bookingsToBookingsDto(List<Booking> bookings);

    @Mapping(source = "userId",target = "user",qualifiedByName = "mapUserFromId")
    @Mapping(source = "eventId",target = "event",qualifiedByName = "mapEventFromId")
    Booking addBookingDtoToBooking(AddBookingDto addBookingDto);
    @Named("mapUserFromId")
    default User mapUserFromId(Long id) {
        if (id==null) return null;
        User user=new User();
        user.setId(id);
        return user;
    }
    @Named("mapEventFromId")
    default Event mapEventFromId(Long id) {
        if (id==null) return null;
        Event event=new Event();
        event.setId(id);
        return event;
    }
}
