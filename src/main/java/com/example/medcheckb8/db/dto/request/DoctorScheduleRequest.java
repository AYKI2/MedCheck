package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;

@Builder
public record DoctorScheduleRequest(
        @NotBlank(message = "Отдел не может быть пустым!")
        @NotNull(message = "Отдел не может быть null!")
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
