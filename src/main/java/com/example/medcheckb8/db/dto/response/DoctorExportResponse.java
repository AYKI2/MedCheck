package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Builder
public record DoctorExportResponse(
        Long id,
        String firstName,
        String lastName,
        String position,
        LocalDate dateOfStart,
        LocalDate dateOfFinish,
        LocalDate date,
        Map<LocalTime, LocalTime> timeFromAndTimeTo
) {
}
