package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Builder
public record ScheduleDateAndTimeResponse(Long doctorId,
                                          Long appointmentId,
                                          Map<LocalDate, Map<LocalTime, LocalTime>> times) {
}
