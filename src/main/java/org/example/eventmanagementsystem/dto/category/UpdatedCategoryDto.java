package org.example.eventmanagementsystem.dto.category;

import jakarta.validation.constraints.NotBlank;

public record UpdatedCategoryDto(@NotBlank String name) {
}
