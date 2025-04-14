package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.auth.*;
import org.example.eventmanagementsystem.email.EmailService;
import org.example.eventmanagementsystem.email.VerificationCodeGenerator;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.exception.WrongVerificationCodeException;
import org.example.eventmanagementsystem.jwt.JwtService;
import org.example.eventmanagementsystem.model.Token;
import org.example.eventmanagementsystem.model.User;
import org.example.eventmanagementsystem.model.enums.TokenType;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final long accessTokenExpiration = 1000 * 60 * 15;
    private final long refreshExpiration = 1000 * 60 * 60 * 24 * 7;

    public ResponseEntity<String> register(RegisterRequestDto registerResponseDto) {
        try {
            String name = registerResponseDto.name();
            String surname = registerResponseDto.surname();
            String email = registerResponseDto.email();
            String username = registerResponseDto.username();
            String password = registerResponseDto.passwordHash();
            String verificationCode = VerificationCodeGenerator.generateCode();
            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .passwordHash(passwordEncoder.encode(password))
                    .username(username)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .verificationCode(verificationCode)
                    .build();
            userRepository.save(user);
            emailService.sendEmail(email, verificationCode);
            return new ResponseEntity<>("Verification code has sent to your email", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> verifyAccount(VerifyRequestDto verifyRequestDto) throws ResourceNotFoundException, WrongVerificationCodeException {
        User user = userRepository.findByEmail(verifyRequestDto.email()).orElseThrow(() -> {
            log.error("User can not found");
            return new ResourceNotFoundException("User can not found");
        });
        if (!(user.getVerificationCode().equals(verifyRequestDto.code()))) {
            throw new WrongVerificationCodeException("Wrong verification Code");
        }
        user.setVerified(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return new ResponseEntity<>("Successfully verified", HttpStatus.OK);
    }

    public ResponseEntity<SingInResponseDto> singIn(SingInRequestDto singInRequestDto) throws ResourceNotFoundException {
        var authenticationToken = new UsernamePasswordAuthenticationToken(singInRequestDto.username(), singInRequestDto.password());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null) {
            log.error("Username or password is incorrect");
            throw new BadCredentialsException("Username or password is incorrect");
        }
        User user = userRepository.findByUsername(singInRequestDto.username()).orElseThrow(() -> {
            log.error("Username not found {}", singInRequestDto.username());
            return new ResourceNotFoundException("Username not found");
        });
        if (!user.isVerified()) {
            log.error("You need to verify your account");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = jwtService.issueToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token access = Token.builder()
                .value(accessToken)
                .issuedAt(Instant.now())
                .expiredAt(Instant.now().plusSeconds(accessTokenExpiration))
                .revokedAt(null)
                .tokenType(TokenType.ACCESSTOKEN)
                .user(user)
                .build();
        tokenService.save(access);

        Token refresh = Token.builder()
                .value(refreshToken)
                .issuedAt(Instant.now())
                .expiredAt(Instant.now().plusSeconds(refreshExpiration))
                .revokedAt(null)
                .tokenType(TokenType.REFRESHTOKEN)
                .user(user)
                .build();
        tokenService.save(refresh);

        return new ResponseEntity<>(new SingInResponseDto(accessToken, refreshToken), HttpStatus.OK);

    }

    public ResponseEntity<String> singOut(String header) throws ResourceNotFoundException {

        String accessToken = header.substring(7);
        log.info("Access token {}", accessToken);
        Token token = tokenService.findByValue(accessToken);
        if (token == null) {
            return new ResponseEntity<>("Token can not found", HttpStatus.NOT_FOUND);
        }
        tokenService.delete(token.getId());
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }

    public ResponseEntity<SingInResponseDto> checkRefreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.refreshToken();
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow();
        if (!jwtService.isValidToken(refreshToken, user.customUserDetails())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String newToken = jwtService.issueToken(user);
        Token token = Token.builder()
                .value(newToken)
                .tokenType(TokenType.ACCESSTOKEN)
                .issuedAt(Instant.now())
                .expiredAt(Instant.now().plusSeconds(accessTokenExpiration))
                .revokedAt(Instant.now().plusSeconds(accessTokenExpiration))
                .user(user)
                .build();
        tokenService.save(token);
        return new ResponseEntity<>(new SingInResponseDto(newToken, refreshToken), HttpStatus.OK);
    }
}
