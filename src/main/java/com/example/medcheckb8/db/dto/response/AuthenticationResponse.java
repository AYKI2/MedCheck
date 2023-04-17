package com.example.medcheckb8.db.dto.response;

import lombok.Builder;
@Builder
public record AuthenticationResponse(String email, String token, String role) {
}
