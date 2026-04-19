package com.kolya.TaskTimeTracker.task.dto;

import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    Long id;
    String name;
    String description;
    TaskStatus status;
}
