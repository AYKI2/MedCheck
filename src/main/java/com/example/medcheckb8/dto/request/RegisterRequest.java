package com.example.medcheckb8.dto.request;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String password
) {
}
