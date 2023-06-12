package com.example.medcheckb8.db.dto.request.appointment;

import com.example.medcheckb8.db.validation.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AddAppointmentRequest(
        @Size(min = 4, max = 20, message = "Служба должна содержать от 4 до 20 символов.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z])$", message = "Служба должна содержать только буквы.") String department,
        Long doctorId,
        LocalDate date,
        LocalTime time,
        @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z])$", message = "Имя должно содержать только буквы.") String fullName,
        @PhoneNumberValid(message = "Неверный номер телефона!")
        String phoneNumber,
        @Email(message = "Неверный формат email!")
        String email,
        String zoneId,
        String link
) {
}
