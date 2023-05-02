package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.ScheduleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getAllSchedule(String word, LocalDate startDate, LocalDate endDate);


}
