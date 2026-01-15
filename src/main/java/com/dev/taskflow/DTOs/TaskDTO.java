package com.dev.taskflow.DTOs;

import com.dev.taskflow.Enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime creationDate,
        Long categoryId,
        String categoryName
) { }
