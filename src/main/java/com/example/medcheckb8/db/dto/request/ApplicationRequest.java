package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ApplicationRequest(
        @Size(min = 2, max = 30, message = "First name must contain between 2 and 30 characters.")
        @NotBlank(message = "First name cannot be empty!")
        @NotNull(message = "First name cannot be null!")
        String name,
        @NotBlank(message = "Phone number cannot be empty!")
        @NotNull(message = "Phone number cannot be empty!")
        @PhoneNumberValid(message = "Invalid phone number!")
        String phoneNumber) {
}
