package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.bookings.AddBookingDto;
import org.example.eventmanagementsystem.dto.bookings.BookingDto;
import org.example.eventmanagementsystem.dto.bookings.UpdatedBookingDto;
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

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAll() {
        return ResponseEntity.ok(bookingService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }
    @PostMapping
    public ResponseEntity<BookingDto> add(@RequestBody AddBookingDto addBookingDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.add(addBookingDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable Long id,@RequestBody UpdatedBookingDto updatedBookingDto) {
        return ResponseEntity.ok(bookingService.update(id,updatedBookingDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
