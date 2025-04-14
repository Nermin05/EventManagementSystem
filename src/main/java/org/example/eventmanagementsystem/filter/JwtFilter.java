package org.example.eventmanagementsystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.jwt.JwtAuthProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final List<JwtAuthProvider> jwtAuthProviders;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<Authentication> authenticationOptional =Optional.empty();
        for (JwtAuthProvider jwtAuthProvider:jwtAuthProviders) {
            authenticationOptional=authenticationOptional.or(()->jwtAuthProvider.getAuthentication(request));
        }
        authenticationOptional.ifPresent(authentication ->
                SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }
}
