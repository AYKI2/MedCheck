package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ResultUserResponse(
        Long resultId,
        LocalDate dateOfIssue,
        LocalTime timeOfIssue,
        String orderNumber,
        String services,
        String file
) {
}
