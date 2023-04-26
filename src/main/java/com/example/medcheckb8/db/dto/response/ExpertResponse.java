package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;

import java.time.LocalDate;

public record ExpertResponse(
        Long id,
        Boolean isActive,
        String firstName,
        String lastName,
        String position,
        String image,
        String description,
        Detachment name,
        LocalDate dataOfFinish
) {
}
