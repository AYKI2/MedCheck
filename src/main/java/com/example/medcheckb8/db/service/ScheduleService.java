package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.DoctorScheduleRequest;
import com.example.medcheckb8.db.dto.request.appointment.InstallByTemplate;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getAllSchedule(String word,
                                          LocalDate startDate,
                                          LocalDate endDate);
    SimpleResponse installByTemplate(InstallByTemplate request);
    SimpleResponse save(DoctorScheduleRequest request);
}
