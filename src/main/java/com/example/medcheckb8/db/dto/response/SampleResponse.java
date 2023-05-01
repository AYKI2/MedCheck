package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalTime;
import java.util.Map;

@Builder
public record SampleResponse(
        Long scheduleId,
        Long doctorId,
        Map<LocalTime, LocalTime> times
      ) {
}
