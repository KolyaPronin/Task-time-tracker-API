package com.kolya.TaskTimeTracker.common.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task with " + id + " not found");
    }
}
