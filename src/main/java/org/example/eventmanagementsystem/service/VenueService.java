package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.venue.AddVenueDto;
import org.example.eventmanagementsystem.dto.venue.UpdatedVenueDto;
import org.example.eventmanagementsystem.dto.venue.VenueDto;
import org.example.eventmanagementsystem.mapper.VenueMapper;
import org.example.eventmanagementsystem.model.Venue;
import org.example.eventmanagementsystem.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    public List<VenueDto> getAll() {
        return venueMapper.venuesToVenuesDto(venueRepository.findAll());
    }

    public VenueDto getById(Long id) {
        return venueMapper.venueToVenueDto(venueRepository.findById(id).orElseThrow(() -> {
            log.error("Venue can not found");
            return new NoSuchElementException("Venue can not found");
        }));
    }

    public VenueDto add(AddVenueDto addVenueDto) {
        Venue venue = venueRepository.save(venueMapper.addVenueDtoToVenue(addVenueDto));
        return venueMapper.venueToVenueDto(venue);
    }

    public VenueDto update(Long id, UpdatedVenueDto updatedVenueDto) {
        Venue venue = venueRepository.findById(id).orElseThrow(() -> {
            log.error("Venue can not found");
            return new NoSuchElementException("Venue can not found");
        });
        venue.setName(updatedVenueDto.name());
        venue.setLocation(updatedVenueDto.location());
        venue.setCapacity(updatedVenueDto.capacity());
        Venue updatedVenue = venueRepository.save(venue);
        return venueMapper.venueToVenueDto(updatedVenue);
    }

    public void delete(Long id) {
        venueRepository.deleteById(id);
    }

}
