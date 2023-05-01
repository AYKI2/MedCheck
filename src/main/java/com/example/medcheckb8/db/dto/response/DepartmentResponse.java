package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Detachment;
import lombok.Builder;

@Builder
public record DepartmentResponse(
        Detachment name
) {
}
