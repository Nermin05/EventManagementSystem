package org.example.eventmanagementsystem.schedule;

import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.enums.EventStatus;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EventExpirationScheduler {

    @Autowired
    private EventRepository eventRepository;
    @Scheduled(cron = "0 0 * * * *")
    public void expireOldEvents() {
        List<Event> events = eventRepository.findByEventStatusAndDateBefore(EventStatus.APPROVED, LocalDate.now());
        events.forEach(event -> event.setEventStatus(EventStatus.EXPIRED));
        eventRepository.saveAll(events);
    }
}
