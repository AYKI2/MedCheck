package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

@Builder
public record AppointmentDoctorResponse(
        Long id,
        String fullName,
        String image,
        String position
) {
}
