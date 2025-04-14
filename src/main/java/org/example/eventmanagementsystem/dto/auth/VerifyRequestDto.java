package org.example.eventmanagementsystem.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record VerifyRequestDto(@NotBlank String email,@NotBlank String code) {
}
