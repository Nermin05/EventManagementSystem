package org.example.eventmanagementsystem.dto.organizer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddOrganizerDto(@NotBlank String name, @NotBlank String email,
                              @NotBlank String phone, @NotBlank String address,
                              @NotNull Long userId) {
}
