package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.organizer.AddOrganizerDto;
import org.example.eventmanagementsystem.dto.organizer.OrganizerDto;
import org.example.eventmanagementsystem.model.Organizer;
import org.example.eventmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizerMapper {
    @Mapping(source = "user.id", target = "userId")
    OrganizerDto organizerToOrganizerDto(Organizer organizer);

    @Mapping(source = "user.id", target = "userId")
    List<OrganizerDto> organizersToOrganizersDto(List<Organizer> organizers);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUserFromId")
    Organizer addOrganizerDtoToOrganizer(AddOrganizerDto addOrganizerDto);

    @Named("mapUserFromId")
    default User mapUserFromId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }
}