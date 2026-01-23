package com.dev.taskflow.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank(message = "O Email é obrigatório")
        String email,
        @NotBlank(message = "A Senha é obrigatória")
        @Size(min = 6, max = 100)
        String password,
        @NotBlank(message = "O Nome é obrigatório")
        String firstName,
        @NotBlank(message = "O Sobrenome é obrigatório")
        String lastName
) {
}
