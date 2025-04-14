package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.review.AddReviewDto;
import org.example.eventmanagementsystem.dto.review.ReviewDto;
import org.example.eventmanagementsystem.dto.review.UpdatedReviewDto;
import org.example.eventmanagementsystem.mapper.ReviewMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.ReviewRepository;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewDto> getAll() {
        return reviewMapper.reviewsToReviewsDto(reviewRepository.findAll());
    }

    public ReviewDto getById(Long id) {
        return reviewMapper.reviewToReviewDto(reviewRepository.findById(id).orElseThrow(() -> {
            log.error("Review can not found");
            return new NoSuchElementException("Review can not found");
        }));
    }

    public ReviewDto add(AddReviewDto addReviewDto) {
        Review review = reviewRepository.save(reviewMapper.addReviewDtoToReview(addReviewDto));
        return reviewMapper.reviewToReviewDto(review);
    }

    public ReviewDto update(Long id, UpdatedReviewDto updatedReviewDto) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            log.error("Review can not found");
            return new NoSuchElementException("Review can not found");
        });
        User user = userRepository.findById(updatedReviewDto.userId()).orElseThrow(() -> {
            log.error("User can not found");
            return new NoSuchElementException("User can not found");
        });
        Event event = eventRepository.findById(updatedReviewDto.eventId()).orElseThrow(() -> {
            log.error("Event can not found");
            return new NoSuchElementException("Event can not found");
        });
        review.setUser(user);
        review.setEvent(event);
        review.setRating(updatedReviewDto.rating());
        review.setComment(updatedReviewDto.comment());
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.reviewToReviewDto(updatedReview);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
