package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.bookings.BookingDto;
import org.example.eventmanagementsystem.dto.user.AddRequestDto;
import org.example.eventmanagementsystem.dto.user.UpdatedRequestDto;
import org.example.eventmanagementsystem.dto.user.UserDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.model.enums.EventStatus;
import org.example.eventmanagementsystem.service.BookingService;
import org.example.eventmanagementsystem.service.EventService;
import org.example.eventmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final EventService eventService;
    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getByIdUser(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDto> add(@RequestBody AddRequestDto addRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(addRequestDto));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UpdatedRequestDto updatedRequestDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.update(id, updatedRequestDto));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Void> approveEvent(@PathVariable Long id) throws ResourceNotFoundException {
        eventService.changeStatus(id, EventStatus.APPROVED);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Void> rejectEvent(@PathVariable Long id) throws ResourceNotFoundException {
        eventService.changeStatus(id, EventStatus.REJECTED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAll());
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<BookingDto> getById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
