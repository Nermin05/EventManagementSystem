package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.review.AddReviewDto;
import org.example.eventmanagementsystem.dto.review.ReviewDto;
import org.example.eventmanagementsystem.dto.review.UpdatedReviewDto;
import org.example.eventmanagementsystem.exception.LateEventDateException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.ReviewMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.repository.BookingRepository;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.ReviewRepository;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public List<ReviewDto> getAll() {
        return reviewMapper.reviewsToReviewsDto(reviewRepository.findAll());
    }

    public ReviewDto getById(Long id) throws ResourceNotFoundException {
        return reviewMapper.reviewToReviewDto(reviewRepository.findById(id).orElseThrow(() -> {
            log.error("Review can not found");
            return new ResourceNotFoundException("Review can not found");
        }));
    }

    public ReviewDto add(AddReviewDto addReviewDto) throws ResourceNotFoundException, LateEventDateException {
        Event event = eventRepository.findById(addReviewDto.eventId()).orElseThrow(() -> {
            log.error("Event can not found");
            return new ResourceNotFoundException("Event can not found");
        });
        User user = userRepository.findById(addReviewDto.userId()).orElseThrow(() -> {
            log.error("User can not found");
            return new ResourceNotFoundException("User can not found");
        });
        if (event.getDate().isAfter(LocalDate.now())) {
            throw new LateEventDateException("You can only review an event after it has ended");
        }
        boolean exists = bookingRepository.existsByUserIdAndEventId(user.getId(), event.getId());
        if (!exists) {
            throw new IllegalStateException("You cannot review an event you didn't attend");
        }
        Review added = reviewMapper.addReviewDtoToReview(addReviewDto);
        added.setUser(user);
        added.setEvent(event);
        Review review = reviewRepository.save(added);
        return reviewMapper.reviewToReviewDto(review);
    }

    public ReviewDto update(Long id, UpdatedReviewDto updatedReviewDto) throws ResourceNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            log.error("Review can not found");
            return new ResourceNotFoundException("Review can not found");
        });
        review.setRating(updatedReviewDto.rating());
        review.setComment(updatedReviewDto.comment());
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.reviewToReviewDto(updatedReview);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
