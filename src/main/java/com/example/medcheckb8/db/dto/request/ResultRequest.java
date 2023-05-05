package com.example.medcheckb8.db.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResultRequest(
        Long departmentId,
        Long patientId,
        LocalDateTime dateOfIssue,
        String file
) {
}
