package com.example.medcheckb8.db.dto.request.appointment;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record AddAppointmentRequest(
        @Size(min = 4, max = 20, message = "Service must contain between 4 and 20 characters.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z]*)*$", message = "Service must contain only letters.")
        String department,
        Long doctorId,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime date,
        @Size(min = 2, max = 30, message = "The name must contain between 2 and 30 characters.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z]*)*$", message = "The name must contain only letters.")
        String fullName,
        @PhoneNumberValid(message = "Invalid phone number!")
        String phoneNumber,
        @Email(message = "Invalid email!")
        String email) {
}
