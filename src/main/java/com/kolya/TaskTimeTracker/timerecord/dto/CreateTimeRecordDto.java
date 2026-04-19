package com.kolya.TaskTimeTracker.timerecord.dto;

import java.time.LocalDateTime;

public record CreateTimeRecordDto(
    Long employeeId,
    Long taskId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String description
) {}
