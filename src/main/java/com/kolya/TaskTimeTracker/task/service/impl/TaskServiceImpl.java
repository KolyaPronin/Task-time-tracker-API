package com.kolya.TaskTimeTracker.task.service.impl;

import com.kolya.TaskTimeTracker.model.Task;
import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;
import com.kolya.TaskTimeTracker.task.mapper.TaskDtoMapper;
import com.kolya.TaskTimeTracker.task.persistence.TaskMapper;
import com.kolya.TaskTimeTracker.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskDtoMapper mapper;

    public TaskServiceImpl(TaskMapper taskMapper, TaskDtoMapper mapper) {
        this.taskMapper = taskMapper;
        this.mapper = mapper;
    }

    public TaskDto createTask(CreateTaskDto dto) {
        Task task = mapper.toEntity(dto);
        taskMapper.insert(task);
        return mapper.toDto(task);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskMapper.findById(id);

        if (task == null) { //потомзаменить на NotFoundException!
            throw new RuntimeException("Task not found with id: " + id);
        }

        return mapper.toDto(task);
    }

    public void updateStatus(Long id, TaskStatus status) {
        taskMapper.updateStatus(id, status.name());
    }

    public List<TaskDto> getAllTask() {
        List<Task> tasks = taskMapper.findAll();
        List<TaskDto> dtos = new ArrayList<>();
        for(Task task : tasks)
            dtos.add(mapper.toDto(task));
        return dtos;
    }
}
