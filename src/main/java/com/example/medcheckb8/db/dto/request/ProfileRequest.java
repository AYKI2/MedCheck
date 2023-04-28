package com.example.medcheckb8.db.dto.request;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProfileRequest(
        Long userId,
        @Size(min = 2, max = 30, message = "First name must contain between 2 and 30 characters.")
        @NotBlank(message = "First name cannot be empty!")
        @NotNull(message = "First name cannot be null!")
        String firstName,
        @Size(min = 2, max = 30, message = "First name must contain between 2 and 30 characters.")
        @NotBlank(message = "Last name cannot be empty!")
        @NotNull(message = "Last name cannot be null!")
        String lastName,
        @NotBlank(message = "Phone number cannot be empty!")
        @NotNull(message = "Phone number cannot be empty!")
        @PhoneNumberValid(message = "Invalid phone number!")
        String phoneNumber,
        @NotBlank(message = "Email cannot be empty!")
        @NotNull(message = "Email cannot be empty!")
        @Email(message = "Not valid email!")
        String email) {
}
