package org.example.eventmanagementsystem.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatedRequestDto {
    private String name;
    private String surname;
    private String email;
    private String username;
    private String passwordHash;
}
