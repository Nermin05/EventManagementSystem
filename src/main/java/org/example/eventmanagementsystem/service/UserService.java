package org.example.eventmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventmanagementsystem.dto.user.AddRequestDto;
import org.example.eventmanagementsystem.dto.user.UpdatedRequestDto;
import org.example.eventmanagementsystem.dto.user.UserDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.mapper.UserMapper;
import org.example.eventmanagementsystem.model.User;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAll() {
        return userMapper.usersToUsersDto(userRepository.findAll());
    }

    public UserDto getById(Long id) throws ResourceNotFoundException {
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(() -> {
            log.error("User can not found");
            return new ResourceNotFoundException("User " + id + " can not found");
        }));
    }

    public UserDto add(AddRequestDto addRequestDto) {
        User user = userRepository.save(userMapper.addRequestDtoToUser(addRequestDto));
        return userMapper.userToUserDto(user);
    }

    public UserDto update(Long id, UpdatedRequestDto updatedRequestDto) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User can not found");
            return new ResourceNotFoundException("User " + id + " can not found");
        });
        user.setName(updatedRequestDto.getName());
        user.setSurname(updatedRequestDto.getSurname());
        user.setUsername(updatedRequestDto.getUsername());
        user.setPasswordHash(updatedRequestDto.getPasswordHash());
        user.setEmail(updatedRequestDto.getEmail());
        User updatedUser = userRepository.save(user);
        return userMapper.userToUserDto(updatedUser);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        if (id == null) {
            throw new ResourceNotFoundException("User can not found");
        }
        userRepository.deleteById(id);
    }
}
