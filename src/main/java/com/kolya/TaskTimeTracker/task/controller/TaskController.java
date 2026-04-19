package com.kolya.TaskTimeTracker.task.controller;

import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;
import com.kolya.TaskTimeTracker.task.dto.UpdateTaskStatusDto;
import com.kolya.TaskTimeTracker.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return service.getAllTask();
    }

    @PostMapping
    public TaskDto create(@RequestBody CreateTaskDto dto) {
        return service.createTask(dto);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id,
                             @RequestBody UpdateTaskStatusDto dto) {
        service.updateStatus(id, dto.status());
    }

}
