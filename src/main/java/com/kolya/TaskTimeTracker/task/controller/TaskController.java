package com.kolya.TaskTimeTracker.task.controller;

import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;
import com.kolya.TaskTimeTracker.task.dto.UpdateTaskStatusDto;
import com.kolya.TaskTimeTracker.task.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable @Positive Long id) {
        return service.getTaskById(id);
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return service.getAllTask();
    }

    @PostMapping
    public TaskDto create(@RequestBody @Valid CreateTaskDto dto) {
        return service.createTask(dto);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable @Positive Long id,
                             @RequestBody @Valid UpdateTaskStatusDto dto) {
        service.updateStatus(id, dto.status());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Positive Long id) {
        service.deleteById(id);
    }
}
