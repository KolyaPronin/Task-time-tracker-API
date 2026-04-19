package com.kolya.TaskTimeTracker.timerecord.controller;

import com.kolya.TaskTimeTracker.timerecord.service.TimeRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timeRecord")
public class TimeRecordController {
    private final TimeRecordService service;
    TimeRecordController(TimeRecordService service) {
        this.service = service;
    }


}
