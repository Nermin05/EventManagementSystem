package org.example.eventmanagementsystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequestDto(@NotBlank String name, @NotBlank String surname,
                                 @Email(message = "email can not be null") String email,
                                 @NotBlank @Size(min = 8) String username,
                                 @NotBlank @Size(min = 8) String passwordHash,
                                 @NotBlank @Size(min = 8) String passwordAgain) {
}
