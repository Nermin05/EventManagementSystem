package org.example.eventmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.eventmanagementsystem.model.enums.UserRole;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private Instant createdAt;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private String issueKey;
    private String verificationCode;
    private boolean isVerified;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class)
    private Set<UserRole> roles;
    @OneToOne(mappedBy = "user")
    private Organizer organizer;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PaymentDetails> paymentDetails;

    public CustomUserDetails customUserDetails() {
        return new CustomUserDetails(this);
    }
}
