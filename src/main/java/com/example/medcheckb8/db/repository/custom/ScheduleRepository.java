package com.example.medcheckb8.db.repository.custom;

import com.example.medcheckb8.db.dto.response.ScheduleResponse;


import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {
    List<ScheduleResponse> getAll();

    List<ScheduleResponse> getAll(String word, LocalDate start, LocalDate end);
}