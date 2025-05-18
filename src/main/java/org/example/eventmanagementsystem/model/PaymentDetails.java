package org.example.eventmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 16, max = 16)
    private String cardNumber;
    @Size(min = 3, max = 3)
    private String cvv;
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}")
    private String expirationDate;
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
