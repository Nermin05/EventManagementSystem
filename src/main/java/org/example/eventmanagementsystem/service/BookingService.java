package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.bookings.AddBookingDto;
import org.example.eventmanagementsystem.dto.bookings.BookingDto;
import org.example.eventmanagementsystem.dto.bookings.UpdatedBookingDto;
import org.example.eventmanagementsystem.exception.LateEventBookingDateTimeException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.BookingMapper;
import org.example.eventmanagementsystem.model.Booking;
import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.User;
import org.example.eventmanagementsystem.model.enums.BookingStatus;
import org.example.eventmanagementsystem.repository.BookingRepository;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public BookingDto getById(Long id) throws ResourceNotFoundException {
        return bookingMapper.bookingToBookingDto(bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Booking can not found: {}",id);
            return new ResourceNotFoundException("Booking can not found");
        }));
    }

    public BookingDto add(AddBookingDto addBookingDto) throws LateEventBookingDateTimeException {
        User user = userRepository.findById(addBookingDto.userId()).orElseThrow();
        Event event = eventRepository.findById(addBookingDto.eventId()).orElseThrow();
        Integer countedBookings = bookingRepository.countByEventAndBookingStatus(event, BookingStatus.REGISTERED);
        BookingStatus bookingStatus;
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new LateEventBookingDateTimeException("You cannot book tickets for past events");
        }
        if (countedBookings + addBookingDto.numTickets() <= event.getVenue().getCapacity()) {
            bookingStatus = BookingStatus.REGISTERED;
        } else {
            bookingStatus = BookingStatus.WAITING_LIST;
        }
        BigDecimal totalPrice = event.isPaid() ? event.getPrice().multiply(BigDecimal.valueOf(addBookingDto.numTickets()))
                : BigDecimal.ZERO;
        Booking booking = Booking.builder()
                .event(event)
                .user(user)
                .bookingDate(LocalDateTime.now())
                .numTickets(addBookingDto.numTickets())
                .totalPrice(totalPrice)
                .bookingStatus(bookingStatus)
                .build();
        return bookingMapper.bookingToBookingDto(booking);
    }

    public BookingDto update(Long id, UpdatedBookingDto updatedBookingDto) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Booking can not found: {}",id);
            return new ResourceNotFoundException("Booking can not found");
        });
        booking.setNumTickets(updatedBookingDto.numTickets());
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.bookingToBookingDto(saved);
    }
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setBookingStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);

        List<Booking> waitingList = bookingRepository.findTopByEventAndBookingStatusOrderByBookingDateAsc(
                booking.getEvent(), BookingStatus.WAITING_LIST);

        if (!waitingList.isEmpty()) {
            Booking next = waitingList.get(0);
            next.setBookingStatus(BookingStatus.REGISTERED);
            bookingRepository.save(next);
        }
    }
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}
