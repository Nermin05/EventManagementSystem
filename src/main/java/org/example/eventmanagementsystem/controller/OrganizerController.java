package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.organizer.AddOrganizerDto;
import org.example.eventmanagementsystem.dto.organizer.OrganizerDto;
import org.example.eventmanagementsystem.dto.organizer.UpdatedOrganizerDto;
import org.example.eventmanagementsystem.service.OrganizerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizers")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping
    public ResponseEntity<List<OrganizerDto>> getAll() {
        return ResponseEntity.ok(organizerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(organizerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<OrganizerDto> add(@RequestBody AddOrganizerDto addOrganizerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizerService.add(addOrganizerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizerDto> update(@PathVariable Long id, @RequestBody UpdatedOrganizerDto updatedOrganizerDto) {
        return ResponseEntity.ok(organizerService.update(id, updatedOrganizerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        organizerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
