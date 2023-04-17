package com.example.medcheckb8.dto.request;

import com.example.medcheckb8.validation.PasswordValid;
import com.example.medcheckb8.validation.PhoneNumberValid;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @Size(min = 2, max = 30, message = "The Firstname must contain between 2 and 30 characters.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z]*)*$", message = "The Firstname must contain only letters.")
        @NotBlank(message = "Firstname cannot be empty!")
        @NotNull(message = "Firstname cannot be empty!")
        String firstName,
        @Size(min = 2, max = 30, message = "The Lastname must contain between 2 and 30 characters.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z]*)*$", message = "The Lastname must contain only letters.")
        @NotBlank(message = "Lastname cannot be empty!")
        @NotNull(message = "Lastname cannot be empty!")
        String lastName,
        @NotBlank(message = "Phone number cannot be empty!")
        @NotNull(message = "Phone number cannot be empty!")
        @PhoneNumberValid(message = "Invalid phone number!")
        String phoneNumber,
        @NotBlank(message = "Email cannot be empty!")
        @NotNull(message = "Email cannot be empty!")
        @Email(message = "Not valid email!")
        String email,
        @PasswordValid(message = "Invalid password!")
        String password
) {
}
