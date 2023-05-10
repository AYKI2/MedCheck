package com.example.medcheckb8.db.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Builder
public record SampleRequest(
        Long appointmentId,
        Long doctorId,
        Map<LocalDate,Map<LocalTime,LocalTime>>times) {
}
