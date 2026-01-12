package com.dev.taskflow.DTOs;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String title,
        String description,
        boolean isFinished,
        LocalDateTime creationDate
) { }
