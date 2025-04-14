package org.example.eventmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "venue_id",nullable = false)
    private Venue venue;
    @ManyToOne
    @JoinColumn(name = "organizer_id",nullable = false)
    private Organizer organizer;
    @Column(nullable = false)
    private BigDecimal price;
    private Instant createdAt;

}
