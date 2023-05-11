package com.example.medcheckb8.db.dto.response.appointment;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AddAppointmentResponse(
        AppointmentDoctorResponse response,
        LocalDateTime dateAndTime) {
}
