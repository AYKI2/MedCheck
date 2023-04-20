package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResultResponse(
        String departmentName,
        LocalDateTime dateOfIssue,
        String orderNumber,
        String file
) {
}
