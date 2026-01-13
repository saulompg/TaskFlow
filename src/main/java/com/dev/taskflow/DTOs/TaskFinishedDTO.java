package com.dev.taskflow.DTOs;

import jakarta.validation.constraints.NotNull;

public record TaskFinishedDTO(
        @NotNull
        Boolean finished
) { }
