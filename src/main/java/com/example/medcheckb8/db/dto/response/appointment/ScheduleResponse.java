package com.example.medcheckb8.db.dto.response.appointment;

import com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ScheduleResponse(
        Long id,
        String fullName,
        String image,
        String position,
        List<ScheduleDateAndTimeResponse> localDateTimes
) {
}
