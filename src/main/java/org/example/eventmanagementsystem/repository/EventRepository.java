package org.example.eventmanagementsystem.repository;

import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> ,CustomEventRepository{
    List<Event> findByEventStatus(EventStatus eventStatus);
    List<Event> findByEventStatusAndDateBefore(EventStatus eventStatus, LocalDate date);
}
