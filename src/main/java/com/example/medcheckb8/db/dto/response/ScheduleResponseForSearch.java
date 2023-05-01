package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.entities.Doctor;

public record ScheduleResponseForSearch(Long id,
                                        Doctor doctor) {
}
