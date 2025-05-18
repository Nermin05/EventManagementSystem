package org.example.eventmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.auth.*;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.exception.WrongVerificationCodeException;
import org.example.eventmanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return authService.register(registerRequestDto);
    }

    @PostMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestBody @Valid VerifyRequestDto verifyRequestDto) throws WrongVerificationCodeException, ResourceNotFoundException {
        return authService.verifyAccount(verifyRequestDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SingInResponseDto> singIn(@RequestBody @Valid SingInRequestDto singInRequestDto) throws ResourceNotFoundException {
        return authService.singIn(singInRequestDto);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> singOut(@RequestHeader("Authorization") String header) throws ResourceNotFoundException {
        return authService.singOut(header);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SingInResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return authService.checkRefreshToken(refreshTokenRequestDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) throws ResourceNotFoundException {
        return authService.forgotPassword(forgotPasswordRequestDto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) throws ResourceNotFoundException, WrongVerificationCodeException {
        return authService.resetPassword(resetPasswordRequestDto);
    }
}
