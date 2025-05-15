package org.example.eventmanagementsystem.dto.category;

import jakarta.validation.constraints.NotBlank;

public record AddCategoryDto(@NotBlank String name) {
}
