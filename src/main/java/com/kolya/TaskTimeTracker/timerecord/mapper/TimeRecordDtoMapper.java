package com.kolya.TaskTimeTracker.timerecord.mapper;

import com.kolya.TaskTimeTracker.model.TimeRecord;
import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;

public class TimeRecordDtoMapper {
    public TimeRecordDto toDto(TimeRecord timeRecord) {
        return new TimeRecordDto(
                timeRecord.getId(),
                timeRecord.getEmployeeId(),
                timeRecord.getTaskId(),
                timeRecord.getStartTime(),
                timeRecord.getEndTime(),
                timeRecord.getDescription()
        );
    }

    public TimeRecord toEntity(CreateTimeRecordDto dto) {
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setEmployeeId(dto.employeeId());
        timeRecord.setTaskId(dto.taskId());
        timeRecord.setStartTime(dto.startTime());
        timeRecord.setEndTime(dto.endTime());
        timeRecord.setDescription(dto.description());
        return timeRecord;
    }
}