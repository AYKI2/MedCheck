package com.example.medcheckb8.db.dto.response.appointment;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AddAppointmentResponse(
        AppointmentDoctorResponse response,
        LocalDate date,
        LocalTime time) {
}
