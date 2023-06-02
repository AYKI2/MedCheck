package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ResultResponse (
    Long patientId,
    String patientFullName,
    String patientPhoneNumber,
    String patientEmail,
    LocalDate dateOfIssue,
    LocalTime timeOfIssue,
    String orderNumber,
    String name,
    String file
    ) {

}
