package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.review.AddReviewDto;
import org.example.eventmanagementsystem.dto.review.ReviewDto;
import org.example.eventmanagementsystem.dto.review.UpdatedReviewDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.ReviewMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewDto> getAll() {
        return reviewMapper.reviewsToReviewsDto(reviewRepository.findAll());
    }

    public ReviewDto getById(Long id) throws ResourceNotFoundException {
        return reviewMapper.reviewToReviewDto(reviewRepository.findById(id).orElseThrow(() -> {
            log.error("Review can not found");
            return new ResourceNotFoundException("Review can not found");
        }));
    }

    public ReviewDto add(AddReviewDto addReviewDto) {
        Review review = reviewRepository.save(reviewMapper.addReviewDtoToReview(addReviewDto));
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
