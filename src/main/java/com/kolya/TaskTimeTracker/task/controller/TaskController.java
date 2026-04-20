package com.kolya.TaskTimeTracker.task.controller;

import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;
import com.kolya.TaskTimeTracker.task.dto.UpdateTaskStatusDto;
import com.kolya.TaskTimeTracker.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "task_endpoints")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @Operation(
            summary = "Get task by ID",
            description = "The request contains the ID of the task being received")
    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable @Positive Long id) {
        return service.getTaskById(id);
    }

    @Operation(summary = "Fetching all existing tasks")
    @GetMapping
    public List<TaskDto> getAllTasks() {
        return service.getAllTask();
    }

    @Operation(
            summary = "Create new task",
            description = "It is necessary to pass in the request the DTO, name and description fields")
    @PostMapping
    public TaskDto create(@RequestBody @Valid CreateTaskDto dto) {
        return service.createTask(dto);
    }

    @Operation(
            summary = "Change tasks status",
            description = "Update task status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable @Positive Long id,
                             @RequestBody @Valid UpdateTaskStatusDto dto) {
        service.updateStatus(id, dto.status());
    }

    @Operation(
            summary = "Delete task by id",
            description = "The request contains the ID of the task being deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Positive Long id) {
        service.deleteById(id);
    }
}
