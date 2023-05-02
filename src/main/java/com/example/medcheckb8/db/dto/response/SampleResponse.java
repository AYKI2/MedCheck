package com.example.medcheckb8.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleResponse {
    private Long appointmentId;
    private Long doctorId;
    private Map<LocalDate,Map<LocalTime,LocalTime>> times;
}
