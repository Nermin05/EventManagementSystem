package org.example.eventmanagementsystem.dto.auth;

public record ResetPasswordRequestDto(String email, String verificationCode,
                                      String newPassword) {
}
