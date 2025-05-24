package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.ticket.AddTicketDto;
import org.example.eventmanagementsystem.dto.ticket.TicketDto;
import org.example.eventmanagementsystem.dto.ticket.UpdatedTicketDto;
import org.example.eventmanagementsystem.exception.NotEnoughSpaceException;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.TicketMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.model.enums.TicketStatus;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.TicketRepository;
import org.example.eventmanagementsystem.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final VenueRepository venueRepository;

    public List<TicketDto> getAll() {
        return ticketMapper.ticketsToTicketsDto(ticketRepository.findAll());
    }

    public TicketDto getById(Long id) throws ResourceNotFoundException {
        return ticketMapper.ticketToTicketDto(ticketRepository.findById(id).orElseThrow(() -> {
            log.error("Ticket can not found");
            return new ResourceNotFoundException("Ticket can not found");
        }));
    }

    public TicketDto add(AddTicketDto addTicketDto) throws NotEnoughSpaceException {
        Ticket ticket = ticketRepository.save(ticketMapper.addTicketDtoToTicket(addTicketDto));
        Event event = ticket.getEvent();
        Venue venue = event.getVenue();
        if (ticket.getStatus() == TicketStatus.VALID) {
            if (venue.getCapacity() <= 0) {
                throw new NotEnoughSpaceException("Event is sold out");
            }
            venue.setCapacity(venue.getCapacity() - 1);
            venueRepository.save(venue);
        }
        return ticketMapper.ticketToTicketDto(ticket);
    }

    public TicketDto update(Long id, UpdatedTicketDto updatedTicketDto) throws ResourceNotFoundException {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> {
            log.error("Ticket can not found");
            return new ResourceNotFoundException("Ticket can not found");
        });
        ticket.setTicketCode(updatedTicketDto.ticketCode());
        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.ticketToTicketDto(updatedTicket);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
