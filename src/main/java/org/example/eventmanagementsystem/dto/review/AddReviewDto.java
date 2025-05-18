package org.example.eventmanagementsystem.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddReviewDto(@NotNull Long userId, @NotNull Long eventId,
                           @NotNull @Min(1) @Max(5) Integer rating, @NotBlank String comment) {
}
