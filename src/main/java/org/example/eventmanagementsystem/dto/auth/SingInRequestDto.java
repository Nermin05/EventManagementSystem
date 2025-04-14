package org.example.eventmanagementsystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SingInRequestDto(@NotBlank String username, @NotBlank
@Size(min = 8) String password) {
}
