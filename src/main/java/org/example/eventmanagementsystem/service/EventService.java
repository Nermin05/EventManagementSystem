package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.event.AddEventDto;
import org.example.eventmanagementsystem.dto.event.EventDto;
import org.example.eventmanagementsystem.dto.event.UpdatedEventDto;
import org.example.eventmanagementsystem.mapper.EventMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.repository.CategoryRepository;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.OrganizerRepository;
import org.example.eventmanagementsystem.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return eventMapper.eventsToEventsDto(eventRepository.findAll());
    }
    public EventDto getById(Long id) {
        return eventMapper.eventToEventDto(eventRepository.findById(id).orElseThrow(()-> {
            log.error("Event can not found");
            return new NoSuchElementException("Event can not found");
        }));
    }
    public EventDto add(AddEventDto addEventDto) {
        Event event = eventRepository.save(eventMapper.addEventDtoToEvent(addEventDto));
        return eventMapper.eventToEventDto(event);
    }
    public EventDto update(Long id, UpdatedEventDto updatedEventDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> {
            log.error("Event can not found");
            return new NoSuchElementException("Event can not found");
        });
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category can not found");
            return new NoSuchElementException("Category can not found");
        });
        Venue venue = venueRepository.findById(id).orElseThrow(() -> {
            log.error("Venue can not found");
            return new NoSuchElementException("Venue can not found");
        });
        Organizer organizer = organizerRepository.findById(id).orElseThrow(() -> {
            log.error("Organizer can not found");
            return new NoSuchElementException("Organizer can not found");
        });
        event.setName(updatedEventDto.getName());
        event.setDescription(updatedEventDto.getDescription());
        event.setDate(updatedEventDto.getDate());
        event.setCategory(category);
        event.setVenue(venue);
        event.setOrganizer(organizer);
        event.setPrice(updatedEventDto.getPrice());
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.eventToEventDto(updatedEvent);
    }
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}

