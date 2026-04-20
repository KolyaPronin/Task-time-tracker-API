package com.kolya.TaskTimeTracker.timerecord.controller;

import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.service.TimeRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "time_record_endpoints")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/time-records")
@Validated
public class TimeRecordController {

    private final TimeRecordService service;
    public TimeRecordController(TimeRecordService service) {
        this.service = service;
    }

    @Operation(summary = "Create record")
    @PostMapping
    public TimeRecordDto create(@RequestBody @Valid CreateTimeRecordDto dto) {
        return service.createTimeRecord(dto);
    }

    @Operation(
            summary = "Get time records by employee and period",
            description = "Returns time records filtered by employee ID and date range (from - to)"
    )
    @GetMapping
    public List<TimeRecordDto> getTimeRecordsByEmployeeAndPeriod(
            @RequestParam
            @Parameter(description = "Employee ID")
            @Positive Long employeeId,
            @RequestParam
            @Parameter(description = "Start date-time (ISO-8601)")
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam
            @Parameter(description = "End date-time (ISO-8601)")
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return service.getTimeRecordsByEmployeeAndPeriod(employeeId, from, to);
    }
}
