package com.example.medcheckb8.db.dto.response;

import lombok.Builder;

@Builder
public record SearchResponse(
        String doctorFirstName,
        String doctorLastName,
        String departmentName
) {

}
