package com.example.medcheckb8.db.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;

@Builder
public record DoctorScheduleRequest(
        Long departmentId,
        Long doctorId,
        LocalDate startDate,
        LocalDate endDate,
        String startTime,
        String endTime,
        int interval,
        String startBreak,
        String endBreak,
        Map<String, Boolean> repeatDays
) {
}
