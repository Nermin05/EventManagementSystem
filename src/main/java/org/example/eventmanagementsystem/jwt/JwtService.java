package org.example.eventmanagementsystem.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.eventmanagementsystem.model.Authority;
import org.example.eventmanagementsystem.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Value("${issue.key}")
    private String issueKey;
    private final long jwtExpiration = 1000 * 60 * 15;
    private final long refreshExpiration = 1000 * 60 * 60 * 24 * 7;

    private SecretKey secretKey() {
        byte[] decode = Decoders.BASE64.decode(issueKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String issueToken(User user) {
        List<String> list = user.getAuthorities().stream()
                .map(Authority::getAuthority).toList();

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .addClaims(Map.of("roles", list,
                        "token_type","access"))
                //.setHeader("type","JWT")
                .signWith(secretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        List<String> list = user.getAuthorities().stream()
                .map(Authority::getAuthority).toList();

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .addClaims(Map.of(
                        "token_type", "refresh"
                ))                //.setHeader("type","JWT")
                .signWith(secretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims verifyToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return verifyToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return verifyToken(token).getExpiration().before(new Date());
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
