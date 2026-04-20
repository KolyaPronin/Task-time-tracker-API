package com.kolya.TaskTimeTracker.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskDto (

    @NotBlank
    @Size(max = 255)
    String name,

    @Size(max = 1000)
    String description
) {}
