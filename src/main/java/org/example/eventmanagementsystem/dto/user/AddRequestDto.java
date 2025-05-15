package org.example.eventmanagementsystem.dto.user;


import jakarta.validation.constraints.NotBlank;

public record AddRequestDto(@NotBlank String name, @NotBlank String surname,
                            @NotBlank String email, @NotBlank String username,
                            @NotBlank String passwordHash) {
}
