package org.example.eventmanagementsystem.dto.review;

public record AddReviewDto(Long userId,Long eventId,Integer rating,String comment) {
}
