package com.example.medcheckb8.db.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResultRequest(
        Long departmentId,
        LocalDateTime dateOfIssue,
        String file
) {
}
