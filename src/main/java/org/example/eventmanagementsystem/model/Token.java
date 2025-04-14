package org.example.eventmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.eventmanagementsystem.model.enums.TokenType;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String value;
    private TokenType tokenType;
    private Instant issuedAt;
    private Instant expiredAt;
    private Instant revokedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public boolean isNotValid() {
        return Instant.now().isAfter(expiredAt) && Instant.now().isAfter(revokedAt);
    }
}
