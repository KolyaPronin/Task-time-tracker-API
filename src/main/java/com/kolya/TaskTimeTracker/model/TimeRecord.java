package com.kolya.TaskTimeTracker.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeRecord {
    Long id;
    Long employeeId;
    Long taskId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String description;
}
