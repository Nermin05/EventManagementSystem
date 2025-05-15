package org.example.eventmanagementsystem.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,ROLE,ORGANIZER;

    @Override
    public String getAuthority() {
        return name();
    }
}
