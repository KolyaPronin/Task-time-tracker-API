package com.kolya.TaskTimeTracker.timerecord.service;

import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRecordService {
    TimeRecordDto createTimeRecord(CreateTimeRecordDto dto);
    List<TimeRecordDto> getTimeRecordsByEmployeeAndPeriod(
            Long employeeId,
            LocalDateTime from,
            LocalDateTime to
    );
}
