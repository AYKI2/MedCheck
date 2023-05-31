package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Status;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentResponse(
        Long appointmentId,
        Long doctorId,
        String doctorFullName,
        String doctorImage,
        String doctorPosition,
        LocalDate date,
        LocalTime time,
        Status status
) {
}
