package com.kolya.TaskTimeTracker.common.exception;

public class TimeRecordOverlapException extends RuntimeException {
    public TimeRecordOverlapException(String message) {
        super(message);
    }
}
