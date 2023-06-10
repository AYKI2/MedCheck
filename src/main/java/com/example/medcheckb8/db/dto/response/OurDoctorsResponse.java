package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;
import lombok.Builder;

@Builder
public record OurDoctorsResponse(
        Long id,
        String firstName,
        String lastName,
        String position,
        String image,
        Detachment name
) {
}
