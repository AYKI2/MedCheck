package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Status;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentResponseId (
        Long appointmentId,
        Long doctorId,
        String patientFirstName,
        String patientLastName,
        String doctorFullName,
        String email,
        String phoneNumber,
        LocalDate date,
        LocalTime time,
        Status status,
        String departmentName
){
}
