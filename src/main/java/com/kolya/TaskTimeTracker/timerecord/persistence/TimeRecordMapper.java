package com.kolya.TaskTimeTracker.timerecord.persistence;

import com.kolya.TaskTimeTracker.model.TimeRecord;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRecordMapper {
    void insert(TimeRecord timeRecord);
    TimeRecord findById(Long id);
    List<TimeRecord> findAll();
    void deleteById(Long id);
    List<TimeRecord> findByEmployeeAndPeriod(
            @Param("employeeId") Long employeeId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}