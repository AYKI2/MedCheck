package com.example.medcheckb8.db.repository.custom;

import com.example.medcheckb8.db.dto.request.SampleRequest;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;


import java.time.LocalDate;
import java.util.List;


public interface ScheduleRepository {
    List<ScheduleResponse> getAll();

    List<ScheduleResponse> getAll(String word, LocalDate start, LocalDate end);
}