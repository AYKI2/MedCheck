package com.example.medcheckb8.db.dto.response.appointment;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record GetAllAppointmentResponse(
        Long appointmentId,
        Long patientId,
        Long doctorId,
        String fullName,
        String phoneNumber,
        String email,
        String department,
        String specialist,
        LocalDate localDate,
        LocalTime localTime,
        boolean status
) {
}
