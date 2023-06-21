package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserGetResultResponse(
        Long patientId,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        List<ResultUserResponse> results
) {
}
