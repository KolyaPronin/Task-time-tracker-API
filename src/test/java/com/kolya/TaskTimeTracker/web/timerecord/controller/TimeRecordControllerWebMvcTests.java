package com.kolya.TaskTimeTracker.web.timerecord.controller;

import com.kolya.TaskTimeTracker.common.exception.InvalidTimeRangeException;
import com.kolya.TaskTimeTracker.common.security.JwtService;
import com.kolya.TaskTimeTracker.timerecord.controller.TimeRecordController;
import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.service.TimeRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TimeRecordController.class)
class TimeRecordControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimeRecordService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    void create_shouldReturn200() throws Exception {
        CreateTimeRecordDto request = new CreateTimeRecordDto(
                1L,
                1L,
                LocalDateTime.of(2026, 4, 20, 10, 0),
                LocalDateTime.of(2026, 4, 20, 12, 0),
                "desk"
        );

        TimeRecordDto response = new TimeRecordDto(
                1L,
                1L,
                1L,
                LocalDateTime.of(2026, 4, 20, 10, 0),
                LocalDateTime.of(2026, 4, 20, 12, 0),
                "desk"
        );

        when(service.createTimeRecord(request)).thenReturn(response);

        mockMvc.perform(post("/api/time-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getByEmployeeAndPeriod_shouldReturn200() throws Exception {
        TimeRecordDto dto = new TimeRecordDto(
                1L,
                1L,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "desk"
        );

        when(service.getTimeRecordsByEmployeeAndPeriod(
                1L,
                LocalDateTime.of(2026, 4, 20, 10, 0),
                LocalDateTime.of(2026, 4, 20, 12, 0)
        )).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/time-records")
                        .param("employeeId", "1")
                        .param("from", "2026-04-20T10:00:00")
                        .param("to", "2026-04-20T12:00:00"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void create_shouldReturn400_whenInvalidTimeRange() throws Exception {
        CreateTimeRecordDto request = new CreateTimeRecordDto(
                1L,
                1L,
                LocalDateTime.of(2026, 4, 20, 12, 0),
                LocalDateTime.of(2026, 4, 20, 10, 0),
                "desk"
        );

        when(service.createTimeRecord(any()))
                .thenThrow(new InvalidTimeRangeException("invalid"));

        mockMvc.perform(post("/api/time-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void get_shouldReturn400_whenMissingParams() throws Exception {
        mockMvc.perform(get("/api/time-records"))
                .andExpect(status().isBadRequest());
    }
}
