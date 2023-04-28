package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Repeat;
import lombok.Builder;

import java.time.LocalTime;
import java.time.Month;
import java.util.Map;

@Builder
public record ScheduleResponse(Long id,
                               Repeat repeatDay,
                               Month date,
                               String position,
                               String fullName,
                               String image,
                               Map<LocalTime, LocalTime> times
) {
}
