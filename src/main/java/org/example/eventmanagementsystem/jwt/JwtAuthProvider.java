package org.example.eventmanagementsystem.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtAuthProvider {

    private static final String ROLE_CLAIM = "roles";
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "bearer";
    private static final String TOKEN_TYPE = "token_type";
    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTH_HEADER))
                .filter(this::isBearerAuth)
                .flatMap(this::getAuthenticationBearer);
    }

    private boolean isBearerAuth(String header) {
        return header.toLowerCase().startsWith(BEARER);
    }

    private Optional<Authentication> getAuthenticationBearer(String header) {
        String token = header.substring("Bearer".length()).trim();
        Claims claims = jwtService.verifyToken(token);
        System.out.println("Claims parsed: " + claims);

        if (claims.getExpiration().before(new Date())) {
            return Optional.empty();
        }

        return Optional.of(getAuthenticationBearer(claims));
    }

    private Authentication getAuthenticationBearer(Claims claims) {
        String username = claims.getSubject();
        String tokenType = claims.get(TOKEN_TYPE, String.class);

        if (ACCESS_TYPE.equals(tokenType)) {
            List<?> roles = claims.get(ROLE_CLAIM, List.class);
            List<GrantedAuthority> authorityList = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(
                    userDetailsService.loadUserByUsername(username),
                    null,
                    authorityList
            );
        } else if (REFRESH_TYPE.equals(tokenType)) {
            return new UsernamePasswordAuthenticationToken(
                    userDetailsService.loadUserByUsername(username),
                    null,
                    Collections.emptyList() // or null
            );
        }

        return null;
    }
}
