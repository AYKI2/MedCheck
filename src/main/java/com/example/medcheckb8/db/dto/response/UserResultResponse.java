package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record UserResultResponse(
        Long resultId,
        Long appointmentId,
        Long patientId,
        Detachment name,
        LocalDate date,
        LocalTime time,
        String orderNumber,
        String file

) {
}
