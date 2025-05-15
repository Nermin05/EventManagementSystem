package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.bookings.AddBookingDto;
import org.example.eventmanagementsystem.dto.bookings.BookingDto;
import org.example.eventmanagementsystem.dto.bookings.UpdatedBookingDto;
import org.example.eventmanagementsystem.exception.LateEventBookingDateTimeException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping
    public ResponseEntity<BookingDto> add(@RequestBody AddBookingDto addBookingDto) throws LateEventBookingDateTimeException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.add(addBookingDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable Long id,@RequestBody UpdatedBookingDto updatedBookingDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(bookingService.update(id,updatedBookingDto));
    }
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
