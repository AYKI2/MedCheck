package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.SampleResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getAllSchedule(String word);
    SampleResponse getDateAndTime();


}
