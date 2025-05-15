package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.organizer.AddOrganizerDto;
import org.example.eventmanagementsystem.dto.organizer.OrganizerDto;
import org.example.eventmanagementsystem.dto.organizer.UpdatedOrganizerDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.OrganizerMapper;
import org.example.eventmanagementsystem.model.Organizer;
import org.example.eventmanagementsystem.repository.OrganizerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizerService {
    private final OrganizerRepository organizerRepository;
    private final OrganizerMapper organizerMapper;
    public List<OrganizerDto> getAll() {
        return organizerMapper.organizersToOrganizersDto(organizerRepository.findAll());
    }
    public OrganizerDto getById(Long id) throws ResourceNotFoundException {
        return organizerMapper.organizerToOrganizerDto(organizerRepository.findById(id).orElseThrow(()-> {
            log.error("Organizer can not found");
            return new ResourceNotFoundException("Organizer can not found");
        }));
    }
    public OrganizerDto add(AddOrganizerDto addOrganizerDto) {
        Organizer organizer = organizerRepository.save(organizerMapper.addOrganizerDtoToOrganizer(addOrganizerDto));
        return organizerMapper.organizerToOrganizerDto(organizer);
    }
    public OrganizerDto update(Long id, UpdatedOrganizerDto updatedOrganizerDto) throws ResourceNotFoundException {
        Organizer organizer = organizerRepository.findById(id).orElseThrow(() -> {
            log.error("Organizer can not found");
            return new ResourceNotFoundException("Organizer can not found");
        });
        organizer.setName(updatedOrganizerDto.name());
        organizer.setEmail(updatedOrganizerDto.email());
        organizer.setPhone(updatedOrganizerDto.phone());
        organizer.setAddress(updatedOrganizerDto.address());
        Organizer saved = organizerRepository.save(organizer);
        return organizerMapper.organizerToOrganizerDto(saved);
    }
    public void delete(Long id) {
        organizerRepository.deleteById(id);
    }
}