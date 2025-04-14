package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.model.Token;
import org.example.eventmanagementsystem.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token findByValue(String value) throws ResourceNotFoundException {
        return tokenRepository.findByValue(value).orElseThrow(() -> {
            log.error("Token can not found");
            return new ResourceNotFoundException("Token can not found");
        });
    }

    public void save(Token token) {
        log.info(token.getValue() + "{} token is saved");
        tokenRepository.save(token);
    }

    public void delete(Long id) {
        log.info("Token is deleted");
        tokenRepository.deleteById(id);
    }

}
