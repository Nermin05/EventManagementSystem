package org.example.eventmanagementsystem.repository;

import org.example.eventmanagementsystem.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue,Long> {
}
