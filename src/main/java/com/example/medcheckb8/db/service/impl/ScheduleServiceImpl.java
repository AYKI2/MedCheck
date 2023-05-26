package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.appointment.InstallByTemplate;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;

import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.entities.Schedule;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.repository.custom.ScheduleRepository;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<ScheduleResponse> getAllSchedule(String word,
                                                 LocalDate startDate,
                                                 LocalDate endDate) {
        return repository.getAll(word, startDate, endDate);
    }

    @Override
    public SimpleResponse installByTemplate(InstallByTemplate request) {
        Doctor fromDoctor = doctorRepository.findById(request.fromId())
                .orElseThrow(() -> new NotFountException("Doctor with id: " + request.fromId() + " not found!"));
        Doctor toDoctor = doctorRepository.findById(request.toId())
                .orElseThrow(() -> new NotFountException("Doctor with id: " + request.toId() + " not found!"));
        Schedule schedule = fromDoctor.getSchedule();
        schedule.setIntervalOfHours(request.interval());
//          изменить таблицу schedule добавить время обеда
//          schedule.setStartBreak(request.startBreak);
//          schedule.setEndBreak(request.endBreak);
        for (ScheduleDateAndTime time : schedule.getDateAndTimes()) {
            time.setIsBusy(false);
        }
        toDoctor.setSchedule(schedule);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added!")
                .build();
    }
}
