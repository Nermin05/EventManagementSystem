package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.venue.AddVenueDto;
import org.example.eventmanagementsystem.dto.venue.VenueDto;
import org.example.eventmanagementsystem.model.Venue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    VenueDto venueToVenueDto(Venue venue);
    List<VenueDto> venuesToVenuesDto(List<Venue> venues);
    Venue addVenueDtoToVenue(AddVenueDto addVenueDto);
}
