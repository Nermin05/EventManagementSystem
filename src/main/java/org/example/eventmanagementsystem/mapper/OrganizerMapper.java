package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.organizer.AddOrganizerDto;
import org.example.eventmanagementsystem.dto.organizer.OrganizerDto;
import org.example.eventmanagementsystem.model.Organizer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizerMapper {
    OrganizerDto organizerToOrganizerDto(Organizer organizer);
    List<OrganizerDto> organizersToOrganizersDto(List<Organizer> organizers);
    Organizer addOrganizerDtoToOrganizer(AddOrganizerDto addOrganizerDto);

}
