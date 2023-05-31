package com.example.medcheckb8.db.dto.request;

import lombok.Builder;

@Builder
public record ResultRequest(
        Long appointmentId,
        String zoneId,
        String file
) {
}
