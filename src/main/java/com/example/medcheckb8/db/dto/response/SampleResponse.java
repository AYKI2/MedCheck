package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalTime;
import java.util.Map;

@Builder
public record SampleResponse(
        Map<LocalTime, LocalTime> times) {
}
