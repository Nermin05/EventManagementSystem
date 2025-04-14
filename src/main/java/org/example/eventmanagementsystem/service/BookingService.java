package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.bookings.AddBookingDto;
import org.example.eventmanagementsystem.dto.bookings.BookingDto;
import org.example.eventmanagementsystem.dto.bookings.UpdatedBookingDto;
import org.example.eventmanagementsystem.mapper.BookingMapper;
import org.example.eventmanagementsystem.model.Booking;
import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.User;
import org.example.eventmanagementsystem.repository.BookingRepository;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    public List<BookingDto> getAll() {
        return bookingMapper.bookingsToBookingsDto(bookingRepository.findAll());
    }
    public BookingDto getById(Long id) {
        return bookingMapper.bookingToBookingDto(bookingRepository.findById(id).orElseThrow(()-> {
            log.error("Booking can not found");
            return new NoSuchElementException("Booking can not found");
        }));
    }
    public BookingDto add(AddBookingDto addBookingDto) {
        Booking booking = bookingRepository.save(bookingMapper.addBookingDtoToBooking(addBookingDto));
        return bookingMapper.bookingToBookingDto(booking);
    }
    public BookingDto update(Long id, UpdatedBookingDto updatedBookingDto) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Booking can not found");
            return new NoSuchElementException("Booking can not found");
        });
        User user = userRepository.findById(updatedBookingDto.getUserId()).orElseThrow(() -> {
            log.error("User can not found");
            return new NoSuchElementException("User can not found");
        });
        Event event = eventRepository.findById(updatedBookingDto.getEventId()).orElseThrow(() -> {
            log.error("Event can not found");
            return new NoSuchElementException("Event can not found");
        });
        booking.setUser(user);
        booking.setEvent(event);
        booking.setNumTickets(updatedBookingDto.getNumTickets());
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.bookingToBookingDto(saved);
    }
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}
