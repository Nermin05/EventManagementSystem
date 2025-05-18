package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.review.AddReviewDto;
import org.example.eventmanagementsystem.dto.review.ReviewDto;
import org.example.eventmanagementsystem.dto.review.UpdatedReviewDto;
import org.example.eventmanagementsystem.exception.LateEventDateException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> add(@RequestBody AddReviewDto addReviewDto) throws ResourceNotFoundException, LateEventDateException {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.add(addReviewDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @RequestBody UpdatedReviewDto updatedReviewDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(reviewService.update(id, updatedReviewDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
