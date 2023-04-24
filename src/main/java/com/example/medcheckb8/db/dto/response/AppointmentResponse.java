package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentResponse(
        AppointmentDoctorResponse response,
        LocalDateTime dateAndTime) {
}
