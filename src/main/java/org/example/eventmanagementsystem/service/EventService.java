package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.event.AddEventDto;
import org.example.eventmanagementsystem.dto.event.EventDto;
import org.example.eventmanagementsystem.dto.event.UpdatedEventDto;
import org.example.eventmanagementsystem.exception.NoActivePricePeriodException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.EventMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.model.enums.EventStatus;
import org.example.eventmanagementsystem.repository.CategoryRepository;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.OrganizerRepository;
import org.example.eventmanagementsystem.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final VenueRepository venueRepository;
    private final OrganizerRepository organizerRepository;

    public List<EventDto> getAll() {
        return eventMapper.eventsToEventsDto(eventRepository.findByEventStatus(EventStatus.APPROVED));
    }

    public EventDto getById(Long id) throws ResourceNotFoundException {
        return eventMapper.eventToEventDto(eventRepository.findById(id).orElseThrow(() -> {
            log.error("Event can not found");
            return new ResourceNotFoundException("Event can not found");
        }));
    }

    public EventDto add(AddEventDto addEventDto) {
        Event event = eventRepository.save(eventMapper.addEventDtoToEvent(addEventDto));
        event.setEventStatus(EventStatus.PENDING_APPROVAL);
        Event saved = eventRepository.save(event);
        return eventMapper.eventToEventDto(saved);
    }

    public EventDto update(Long id, UpdatedEventDto updatedEventDto) throws ResourceNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(() -> {
            log.error("Event can not found");
            return new ResourceNotFoundException("Event can not found");
        });
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category can not found");
            return new ResourceNotFoundException("Category can not found");
        });
        Venue venue = venueRepository.findById(id).orElseThrow(() -> {
            log.error("Venue can not found");
            return new ResourceNotFoundException("Venue can not found");
        });
        Organizer organizer = organizerRepository.findById(id).orElseThrow(() -> {
            log.error("Organizer can not found");
            return new ResourceNotFoundException("Organizer can not found");
        });
        event.setName(updatedEventDto.name());
        event.setDescription(updatedEventDto.description());
        event.setDate(updatedEventDto.date());
        event.setCategory(category);
        event.setVenue(venue);
        event.setOrganizer(organizer);
        event.setMinPrice(updatedEventDto.minPrice());
        event.setMaxPrice(updatedEventDto.maxPrice());
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.eventToEventDto(updatedEvent);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<EventDto> search(String name, String date, String category, String location,
                                 BigDecimal minPrice, BigDecimal maxPrice) {
        List<Event> events = eventRepository.search(name, date, category, location, minPrice, maxPrice);
        return eventMapper.eventsToEventsDto(events);
    }

    public void changeStatus(Long id, EventStatus eventStatus) throws ResourceNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(() -> {
            log.error("Event can not found");
            return new ResourceNotFoundException("Event can not found");
        });
        event.setEventStatus(eventStatus);
        eventRepository.save(event);
    }

    public BigDecimal getCurrentPrice(Event event, LocalDateTime now) throws NoActivePricePeriodException {
        return event.getPricePeriods().stream()
                .filter(p -> !now.isBefore(p.getStartDate()) && !now.isAfter(p.getEndDate()))
                .map(PricePeriod::getPrice)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No active price period");
                    return new NoActivePricePeriodException("No active price period");
                });
    }
}

