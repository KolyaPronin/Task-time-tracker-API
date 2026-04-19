package com.kolya.TaskTimeTracker.task.persistence;

import com.kolya.TaskTimeTracker.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    void insert (Task task);
    Task findById(Long id);
    List<Task> findAll();
    void updateStatus(@Param("id") Long id,
                      @Param("status") String status);
    void deleteById(Long id);
}
