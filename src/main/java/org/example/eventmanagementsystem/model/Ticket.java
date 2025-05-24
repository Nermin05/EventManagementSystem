package org.example.eventmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.eventmanagementsystem.model.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "booking_id",nullable = false)
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "event_id",nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false,unique = true)
    private String ticketCode;
    @Column(nullable = false)
    private BigDecimal minPrice;
    @Column(nullable = false)
    private BigDecimal maxPrice;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private Instant createdAt;

}
