package com.kolya.TaskTimeTracker.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timeStamp,
    int status,
    String error,
    String message,
    String path
){}
