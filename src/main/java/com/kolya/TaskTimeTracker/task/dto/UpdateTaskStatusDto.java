package com.kolya.TaskTimeTracker.task.dto;

import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusDto(
        @NotNull
        TaskStatus status
) {}