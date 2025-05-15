package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.venue.AddVenueDto;
import org.example.eventmanagementsystem.dto.venue.UpdatedVenueDto;
import org.example.eventmanagementsystem.dto.venue.VenueDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venues")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @GetMapping
    public ResponseEntity<List<VenueDto>> getAll() {
        return ResponseEntity.ok(venueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(venueService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VenueDto> add(@RequestBody AddVenueDto addVenueDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.add(addVenueDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> update(@PathVariable Long id, @RequestBody UpdatedVenueDto updatedVenueDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(venueService.update(id, updatedVenueDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        venueService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
