package com.example.medcheckb8.db.dto.request;

import lombok.Builder;

@Builder
public record ProfileRequest(String firstName,
                             String lastName,
                             String phoneNumber,
                             String email) {
}
