package com.example.medcheckb8.db.dto.response.appointment;

import lombok.Builder;

@Builder
public record AppointmentDoctorResponse(
        Long id,
        Long appointmentId,
        String fullName,
        String image,
        String position
) {
}
