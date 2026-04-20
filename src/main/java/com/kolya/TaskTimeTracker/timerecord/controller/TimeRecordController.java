package com.kolya.TaskTimeTracker.timerecord.controller;

import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.service.TimeRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/time-records")
@Validated
public class TimeRecordController {

    private final TimeRecordService service;

    public TimeRecordController(TimeRecordService service) {
        this.service = service;
    }

    @PostMapping
    public TimeRecordDto create(@RequestBody @Valid CreateTimeRecordDto dto) {
        return service.createTimeRecord(dto);
    }

    @GetMapping
    public List<TimeRecordDto> getTimeRecordsByEmployeeAndPeriod(
            @RequestParam
            @Positive Long employeeId,
            @RequestParam
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return service.getTimeRecordsByEmployeeAndPeriod(employeeId, from, to);
    }
}
