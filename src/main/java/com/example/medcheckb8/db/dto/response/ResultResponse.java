package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResultResponse (
    Long patientId,
    String patientPhoneNumber,
    String patientEmail,
    LocalDateTime dateOfIssue,
    Detachment name,
    String file
    ) {

}
