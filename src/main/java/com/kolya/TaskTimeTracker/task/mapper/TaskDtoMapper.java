package com.kolya.TaskTimeTracker.task.mapper;

import com.kolya.TaskTimeTracker.model.Task;
import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoMapper {
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus()
        );
    }

    public Task toEntity(CreateTaskDto dto){
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(TaskStatus.NEW);
        return task;
    }
}
