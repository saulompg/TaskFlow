package com.dev.taskflow.DTOs;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String role
) {
}
