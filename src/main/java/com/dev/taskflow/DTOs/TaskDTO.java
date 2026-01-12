package com.dev.taskflow.DTOs;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String title,
        String description,
        Boolean finished,
        LocalDateTime creationDate
) { }
