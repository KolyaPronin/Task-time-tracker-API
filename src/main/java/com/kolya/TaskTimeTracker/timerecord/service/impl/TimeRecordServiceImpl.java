package com.kolya.TaskTimeTracker.timerecord.service.impl;

import com.kolya.TaskTimeTracker.model.TimeRecord;
import com.kolya.TaskTimeTracker.task.mapper.TaskDtoMapper;
import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.mapper.TimeRecordDtoMapper;
import com.kolya.TaskTimeTracker.timerecord.persistence.TimeRecordMapper;
import com.kolya.TaskTimeTracker.timerecord.service.TimeRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeRecordServiceImpl implements TimeRecordService {
    private final TimeRecordMapper timeRecordMapper;
    private final TimeRecordDtoMapper mapper;
    TimeRecordServiceImpl(TimeRecordMapper timeRecordMapper, TimeRecordDtoMapper mapper){
        this.timeRecordMapper = timeRecordMapper;
        this.mapper = mapper;
    }

    public TimeRecordDto createTimeRecord(CreateTimeRecordDto dto) {
        if (dto.endTime().isBefore(dto.startTime())) {//заменить на свой обработчик
            throw new IllegalArgumentException("endTime should be after startTime");
        }
        TimeRecord record = mapper.toEntity(dto);
        timeRecordMapper.insert(record);

        return mapper.toDto(record);
    }

    public List<TimeRecordDto> getTimeRecordsByEmployeeAndPeriod(Long employeeId,
                                                                 LocalDateTime from,
                                                                 LocalDateTime to) {
        List<TimeRecord> records =
                timeRecordMapper.findByEmployeeAndPeriod(employeeId, from, to);
        List<TimeRecordDto> dtos = new ArrayList<>();
        for(TimeRecord record : records){
            dtos.add(mapper.toDto(record));
        }
        return  dtos;
    }

}
