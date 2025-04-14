package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.review.AddReviewDto;
import org.example.eventmanagementsystem.dto.review.ReviewDto;
import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.Review;
import org.example.eventmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "event.id",target = "eventId")
    ReviewDto reviewToReviewDto(Review review);
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "event.id",target = "eventId")
    List<ReviewDto> reviewsToReviewsDto(List<Review> reviews);
    @Mapping(source = "userId",target = "user",qualifiedByName = "userIdToUser")
    @Mapping(source = "eventId",target = "event",qualifiedByName = "eventIdToEvent")
    Review addReviewDtoToReview(AddReviewDto addReviewDto);
    @Named("userIdToUser")
    default User userIdToUser(Long id) {
        if (id==null) return null;
        User user=new User();
        user.setId(id);
        return user;
    }
    @Named("eventIdToEvent")
    default Event eventIdToEvent(Long id) {
        if (id==null) return null;
        Event event=new Event();
        event.setId(id);
        return event;
    }

}
