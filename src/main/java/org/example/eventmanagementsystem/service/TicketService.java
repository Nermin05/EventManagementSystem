package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.ticket.AddTicketDto;
import org.example.eventmanagementsystem.dto.ticket.TicketDto;
import org.example.eventmanagementsystem.dto.ticket.UpdatedTicketDto;
import org.example.eventmanagementsystem.mapper.TicketMapper;
import org.example.eventmanagementsystem.model.*;
import org.example.eventmanagementsystem.repository.BookingRepository;
import org.example.eventmanagementsystem.repository.EventRepository;
import org.example.eventmanagementsystem.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    public List<TicketDto> getAll() {
        return ticketMapper.ticketsToTicketsDto(ticketRepository.findAll());
    }

    public TicketDto getById(Long id) {
        return ticketMapper.ticketToTicketDto(ticketRepository.findById(id).orElseThrow(() -> {
            log.error("Ticket can not found");
            return new NoSuchElementException("Ticket can not found");
        }));
    }

    public TicketDto add(AddTicketDto addTicketDto) {
        Ticket ticket = ticketRepository.save(ticketMapper.addTicketDtoToTicket(addTicketDto));
        return ticketMapper.ticketToTicketDto(ticket);
    }

    public TicketDto update(Long id, UpdatedTicketDto updatedTicketDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> {
            log.error("Ticket can not found");
            return new NoSuchElementException("Ticket can not found");
        });
        Booking booking = bookingRepository.findById(updatedTicketDto.bookingId()).orElseThrow(() -> {
            log.error("Booking can not found");
            return new NoSuchElementException("Booking can not found");
        });
        Event event = eventRepository.findById(updatedTicketDto.eventId()).orElseThrow(() -> {
            log.error("Event can not found");
            return new NoSuchElementException("Event can not found");
        });
        ticket.setBooking(booking);
        ticket.setEvent(event);
        ticket.setTicketCode(updatedTicketDto.ticketCode());
        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.ticketToTicketDto(updatedTicket);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
