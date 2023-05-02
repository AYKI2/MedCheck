package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.ScheduleResponse;


import com.example.medcheckb8.db.repository.custom.ScheduleRepository;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final com.example.medcheckb8.db.repository.ScheduleRepository scheduleRepository;


    @Override
    public List<ScheduleResponse> getAllSchedule(String word, LocalDate startDate, LocalDate endDate) {
        return repository.getAll(word, startDate, endDate);
    }


}
