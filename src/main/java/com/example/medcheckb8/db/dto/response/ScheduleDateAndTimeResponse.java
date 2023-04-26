package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ScheduleDateAndTimeResponse(
        LocalDate date,
        LocalTime timeFrom,
        LocalTime timeTo,
        boolean isBusy
) {
}
