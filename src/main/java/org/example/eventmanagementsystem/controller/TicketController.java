package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.ticket.AddTicketDto;
import org.example.eventmanagementsystem.dto.ticket.TicketDto;
import org.example.eventmanagementsystem.dto.ticket.UpdatedTicketDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TicketDto> add(@RequestBody AddTicketDto addTicketDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.add(addTicketDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> update(@PathVariable Long id, @RequestBody UpdatedTicketDto updatedTicketDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(ticketService.update(id, updatedTicketDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
