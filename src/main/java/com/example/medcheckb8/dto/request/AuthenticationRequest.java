package com.example.medcheckb8.dto.request;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
        String email,
        String password
) {
}
