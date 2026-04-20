package com.kolya.TaskTimeTracker.timerecord.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateTimeRecordDto(
    @NotNull
    @Positive
    Long employeeId,

    @NotNull
    @Positive
    Long taskId,

    @NotNull
    LocalDateTime startTime,

    @NotNull
    LocalDateTime endTime,

    @NotBlank
    @Size(max = 1000)
    String description
) {}
