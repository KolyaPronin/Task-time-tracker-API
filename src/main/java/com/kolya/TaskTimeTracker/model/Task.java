package com.kolya.TaskTimeTracker.model;

import com.kolya.TaskTimeTracker.model.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    Long id;
    String name;
    String description;
    TaskStatus status;
}
