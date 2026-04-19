package com.kolya.TaskTimeTracker.task.service;

import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(CreateTaskDto dto);
    TaskDto getTaskById(Long id);
    void updateStatus(Long id, TaskStatus status);
    List<TaskDto> getAllTask();
}
