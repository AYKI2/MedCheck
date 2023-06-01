package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;

@Builder
public record DoctorScheduleRequest(
        String department,
        Long doctorId,
        @FutureOrPresent
        LocalDate startDate,
        @FutureOrPresent
        LocalDate endDate,
        String startTime,
        String endTime,
        int interval,
        String startBreak,
        String endBreak,
        Map<String, Boolean> repeatDays
) {
}
