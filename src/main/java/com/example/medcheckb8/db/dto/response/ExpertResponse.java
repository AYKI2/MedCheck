package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ExpertResponse(
        Long id,
        Boolean isActive,
        String firstName,
        String lastName,
        String position,
        String image,
        Detachment name,
        Long departmentId,
        LocalDate dataOfFinish
) {
}
