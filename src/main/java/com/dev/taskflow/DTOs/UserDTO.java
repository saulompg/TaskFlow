package com.dev.taskflow.DTOs;

import com.dev.taskflow.Enums.UserRole;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String firstName,
        String lastName,
        UserRole role
) {
}
