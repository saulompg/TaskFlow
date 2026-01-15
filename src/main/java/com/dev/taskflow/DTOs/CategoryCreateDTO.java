package com.dev.taskflow.DTOs;

import com.dev.taskflow.Entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO(
        @NotBlank(message = "O Nome é obrigatório")
        @Size(min = 3, max = 100, message = "O Nome deve ter entre 3 e 100 caracteres")
        String name,
        String color
) {
    public Category toEntity() {
        return new Category(this.name, this.color);
    }
}
