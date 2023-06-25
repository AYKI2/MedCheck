package com.example.medcheckb8.db.dto.response.appointment;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AddAppointmentResponse(
        AppointmentDoctorResponse response,
        LocalDate date,
        String dayOfWeek,
        String timeFrom,
        String timeTo) {
}
