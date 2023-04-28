package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

@Builder
public record SearchResponse(
        Long doctorId,
        Long departmentId,
        String doctorFirstName,
        String doctorLastName,
        String doctorPosition,
        String departmentName
) {

}
