package com.kolya.TaskTimeTracker.task.dto;

import com.kolya.TaskTimeTracker.model.enums.TaskStatus;

public record UpdateTaskStatusDto(TaskStatus status) {}