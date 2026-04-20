package com.kolya.TaskTimeTracker.unit.timerecord.service;

import com.kolya.TaskTimeTracker.common.exception.InvalidTimeRangeException;
import com.kolya.TaskTimeTracker.common.exception.TimeRecordOverlapException;
import com.kolya.TaskTimeTracker.model.TimeRecord;
import com.kolya.TaskTimeTracker.timerecord.dto.CreateTimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.dto.TimeRecordDto;
import com.kolya.TaskTimeTracker.timerecord.mapper.TimeRecordDtoMapper;
import com.kolya.TaskTimeTracker.timerecord.persistence.TimeRecordMapper;
import com.kolya.TaskTimeTracker.timerecord.service.impl.TimeRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceUnitTests {

    @Mock
    private TimeRecordMapper timeRecordMapper;

    @Mock
    private TimeRecordDtoMapper mapper;

    @InjectMocks
    private TimeRecordServiceImpl service;

    @Test
    void create_shouldReturnDto_whenValid() {
        CreateTimeRecordDto dto = mock(CreateTimeRecordDto.class);
        TimeRecord entity = new TimeRecord();
        TimeRecordDto resultDto = new TimeRecordDto(
                1L,
                1L,
                1L,
                LocalDateTime.of(2026, 4, 20, 10, 0),
                LocalDateTime.of(2026, 4, 20, 12, 0),
                "desk"
        );

        when(dto.startTime()).thenReturn(LocalDateTime.now());
        when(dto.endTime()).thenReturn(LocalDateTime.now().plusHours(1));
        when(dto.employeeId()).thenReturn(1L);

        when(timeRecordMapper.findByEmployeeAndPeriod(anyLong(), any(), any()))
                .thenReturn(List.of());

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(resultDto);

        TimeRecordDto result = service.createTimeRecord(dto);

        verify(timeRecordMapper).insert(entity);
        assertEquals(resultDto, result);
    }

    @Test
    void create_shouldThrow_whenEndBeforeStart() {
        CreateTimeRecordDto dto = mock(CreateTimeRecordDto.class);

        when(dto.startTime()).thenReturn(LocalDateTime.now());
        when(dto.endTime()).thenReturn(LocalDateTime.now().minusHours(1));

        assertThrows(InvalidTimeRangeException.class,
                () -> service.createTimeRecord(dto));
    }

    @Test
    void create_shouldThrow_whenOverlapExists() {
        CreateTimeRecordDto dto = mock(CreateTimeRecordDto.class);

        when(dto.startTime()).thenReturn(LocalDateTime.now());
        when(dto.endTime()).thenReturn(LocalDateTime.now().plusHours(1));
        when(dto.employeeId()).thenReturn(1L);

        TimeRecord existing = new TimeRecord();
        existing.setStartTime(LocalDateTime.now());
        existing.setEndTime(LocalDateTime.now().plusMinutes(30));

        when(timeRecordMapper.findByEmployeeAndPeriod(anyLong(), any(), any()))
                .thenReturn(List.of(existing));

        assertThrows(TimeRecordOverlapException.class,
                () -> service.createTimeRecord(dto));
    }

    @Test
    void get_shouldReturnList() {
        TimeRecord record = new TimeRecord();
        TimeRecordDto dto = new TimeRecordDto(
                1L,
                1L,
                1L,
                LocalDateTime.of(2026, 4, 20, 10, 0),
                LocalDateTime.of(2026, 4, 20, 12, 0),
                "desk"
        );

        when(timeRecordMapper.findByEmployeeAndPeriod(anyLong(), any(), any()))
                .thenReturn(List.of(record));

        when(mapper.toDto(record)).thenReturn(dto);

        List<TimeRecordDto> result =
                service.getTimeRecordsByEmployeeAndPeriod(1L, LocalDateTime.now(), LocalDateTime.now());

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}