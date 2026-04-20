package com.kolya.TaskTimeTracker.unit.task.service;



import com.kolya.TaskTimeTracker.common.exception.TaskNotFoundException;
import com.kolya.TaskTimeTracker.model.Task;
import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import com.kolya.TaskTimeTracker.task.dto.CreateTaskDto;
import com.kolya.TaskTimeTracker.task.dto.TaskDto;
import com.kolya.TaskTimeTracker.task.mapper.TaskDtoMapper;
import com.kolya.TaskTimeTracker.task.persistence.TaskMapper;
import com.kolya.TaskTimeTracker.task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTests {
    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskDtoMapper mapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_shouldReturnDto() {
        CreateTaskDto createDto = new CreateTaskDto("test", "desc");
        Task task = new Task();
        TaskDto dto = new TaskDto();

        when(mapper.toEntity(createDto)).thenReturn(task);
        when(mapper.toDto(task)).thenReturn(dto);
        TaskDto result = taskService.createTask(createDto);
        verify(taskMapper).insert(task);
        assertEquals(dto, result);
    }

    @Test
    void getTaskById_shouldReturnDto() {
        Task task = new Task();
        TaskDto dto = new TaskDto();

        when(taskMapper.findById(1L)).thenReturn(task);
        when(mapper.toDto(task)).thenReturn(dto);
        TaskDto result = taskService.getTaskById(1L);
        assertEquals(dto, result);
    }

    @Test
    void getTaskById_shouldThrowException() {
        when(taskMapper.findById(1L)).thenReturn(null);
        assertThrows(TaskNotFoundException.class,
                () -> taskService.getTaskById(1L));
    }

    @Test
    void updateStatus_shouldCallUpdate() {
        Task task = new Task();

        when(taskMapper.findById(1L)).thenReturn(task);
        taskService.updateStatus(1L, TaskStatus.DONE);
        verify(taskMapper).updateStatus(1L, TaskStatus.DONE.name());
    }

    @Test
    void getAllTask_shouldReturnList() {
        Task task = new Task();
        TaskDto dto = new TaskDto();

        when(taskMapper.findAll()).thenReturn(List.of(task));
        when(mapper.toDto(task)).thenReturn(dto);
        List<TaskDto> result = taskService.getAllTask();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void deleteById_shouldCallDelete() {
        Task task = new Task();

        when(taskMapper.findById(1L)).thenReturn(task);
        taskService.deleteById(1L);
        verify(taskMapper).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowException() {
        when(taskMapper.findById(1L)).thenReturn(null);
        assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteById(1L));
    }
}
