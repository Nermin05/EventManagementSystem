package org.example.eventmanagementsystem.dto.review;

public record UpdatedReviewDto(Long userId,Long eventId,Integer rating,String comment){
}
