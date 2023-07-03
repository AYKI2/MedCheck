package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(Long id,
                           String firstName,
                           String lastName,
                           String phoneNumber,
                           String email) {
}
