package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.event.AddEventDto;
import org.example.eventmanagementsystem.dto.event.EventDto;
import org.example.eventmanagementsystem.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "category.id",target = "categoryId")
    @Mapping(source = "venue.id",target = "venueId")
    @Mapping(source = "organizer.id",target = "organizerId")
    EventDto eventToEventDto(Event event);

    @Mapping(source = "category.id",target = "categoryId")
    @Mapping(source = "venue.id",target = "venueId")
    @Mapping(source = "organizer.id",target = "organizerId")
    List<EventDto> eventsToEventsDto(List<Event> events);

    @Mapping(source = "categoryId",target = "category",qualifiedByName = "mapCategoryFromId")
    @Mapping(source = "venueId",target = "venue",qualifiedByName = "mapVenueFromId")
    @Mapping(source = "organizerId",target = "organizer",qualifiedByName = "mapOrganizerFromId")
    Event addEventDtoToEvent(AddEventDto addEventDto);
    @Named("mapCategoryFromId")
    default Category mapCategoryFromId(Long id) {
        if (id==null) return null;
        Category category=new Category();
        category.setId(id);
        return category;
    }
    @Named("mapVenueFromId")
    default Venue mapVenueFromId(Long id) {
        if (id==null) return null;
        Venue venue=new Venue();
        venue.setId(id);
        return venue;
    }
    @Named("mapOrganizerFromId")
    default Organizer mapOrganizerFromId(Long id) {
        if (id==null) return null;
        Organizer organizer=new Organizer();
        organizer.setId(id);
        return organizer;
    }
}
