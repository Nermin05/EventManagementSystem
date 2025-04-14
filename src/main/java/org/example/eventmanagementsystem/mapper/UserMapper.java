package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.user.AddRequestDto;
import org.example.eventmanagementsystem.dto.user.UpdatedRequestDto;
import org.example.eventmanagementsystem.dto.user.UserDto;
import org.example.eventmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = ".", target = "fullName", qualifiedByName = "mapFullName")
    UserDto userToUserDto(User user);
    @Mapping(source = ".", target = "fullName", qualifiedByName = "mapFullName")
    List<UserDto> usersToUsersDto(List<User> user);

    User addRequestDtoToUser(AddRequestDto addRequestDto);
    @Named("mapFullName")
    default String mapFullName(User user) {
        return user.getName() + " " + user.getSurname();
    }
}
