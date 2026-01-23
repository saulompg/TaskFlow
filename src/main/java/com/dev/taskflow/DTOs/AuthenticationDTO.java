package com.dev.taskflow.DTOs;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationDTO(
        @NotEmpty(message = "Campo obrigatório")
        String email,
        @NotEmpty(message = "Campo obrigatório")
        String password
) { }
