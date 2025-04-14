package org.example.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.dto.user.AddRequestDto;
import org.example.eventmanagementsystem.dto.user.UpdatedRequestDto;
import org.example.eventmanagementsystem.dto.user.UserDto;
import org.example.eventmanagementsystem.exception.ResourceNotFoundException;
import org.example.eventmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getById(id));
    }
    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody AddRequestDto addRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(addRequestDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@RequestBody UpdatedRequestDto updatedRequestDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.update(id,updatedRequestDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
