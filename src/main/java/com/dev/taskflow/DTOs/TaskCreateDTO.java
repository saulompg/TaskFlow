package com.dev.taskflow.DTOs;

import com.dev.taskflow.Entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateDTO(
        @NotBlank(message = "O Título é obrigatório")
        @Size(min = 3, max = 100, message = "O Título deve ter entre 3 e 100 caracteres")
        String title,
        @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
        String description
) {
        public Task toEntity() {
                return new Task(this.title, this.description);
        }
}
