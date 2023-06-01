package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;
import lombok.Builder;

@Builder
public record DoctorResponse(
        Long id,
        String firstName,
        String lastName,
        String position,
        String image,
        String description,
        Detachment name,
        Long departmentId
) {
}
