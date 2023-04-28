package com.example.medcheckb8.db.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DoctorUpdateRequest(
        Long doctorId,
        @Size(min = 2, max = 30, message = "First name must contain between 2 and 30 characters.")
        @NotBlank(message = "First name cannot be empty!")
        @NotNull(message = "First name cannot be null!")
        String firstName,
        @Size(min = 2, max = 30, message = "The Last name must contain between 2 and 30 characters.")
        @NotBlank(message = "Last name cannot be empty!")
        @NotNull(message = "Last name cannot be null!")
        String lastName,
        @NotBlank(message = "Position name cannot be empty!")
        @NotNull(message = "Position name cannot be null!")
        String position,
        @NotBlank(message = "Image cannot be empty!")
        @NotNull(message = "Image cannot be null!")
        String image,
        @NotBlank(message = "Description cannot be empty!")
        @NotNull(message = "Description cannot be null!")
        String description
) {
}
