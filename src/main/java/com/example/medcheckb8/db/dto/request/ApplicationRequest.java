package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ApplicationRequest(
        @NotBlank(message = "Name cannot be empty!")
        String name,
        @NotBlank(message = "Phone number cannot be empty!")
        @PhoneNumberValid
        String phoneNumber) {
}
