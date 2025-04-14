package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.event.AddEventDto;
import org.example.eventmanagementsystem.dto.event.EventDto;
import org.example.eventmanagementsystem.dto.event.UpdatedEventDto;
import org.example.eventmanagementsystem.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }
    @PostMapping
    public ResponseEntity<EventDto> add(@RequestBody AddEventDto addEventDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.add(addEventDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable Long id,@RequestBody UpdatedEventDto updatedEventDto) {
        return ResponseEntity.ok(eventService.update(id,updatedEventDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
