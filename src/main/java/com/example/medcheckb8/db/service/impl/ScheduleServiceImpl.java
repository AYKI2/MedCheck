package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.appointment.InstallByTemplate;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;

import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.entities.Schedule;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.repository.custom.ScheduleRepository;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final DoctorRepository doctorRepository;
    private final com.example.medcheckb8.db.repository.ScheduleRepository scheduleRepository;

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
        Schedule schedule = new Schedule();
        if(fromDoctor.getSchedule() == null) throw new NotFountException(String.format("The doctor with id: %d does not have a schedule!",fromDoctor.getId()));
        schedule.setDoctor(toDoctor);
        schedule.setDepartment(toDoctor.getDepartment());
        Map<Repeat, Boolean> repeatDay = fromDoctor.getSchedule().getRepeatDay();
        schedule.setRepeatDay(repeatDay);
        //Found shared references to a collection: com.example.medcheckb8.db.entities.Schedule.repeatDay
        schedule.setDataOfStart(fromDoctor.getSchedule().getDataOfStart());
        schedule.setDataOfFinish(fromDoctor.getSchedule().getDataOfFinish());
        LocalTime startBreak = LocalTime.parse(request.startBreak());
        LocalTime endBreak = LocalTime.parse(request.endBreak());
        List<ScheduleDateAndTime> dateAndTimes = fromDoctor.getSchedule().getDateAndTimes();
        int interval = fromDoctor.getSchedule().getIntervalOfHours();;
        int hour = 0;
        while(interval >= 60){
            interval -= 60;
            hour++;
        }
        for (ScheduleDateAndTime time : dateAndTimes) {
            time.setSchedule(schedule);
            if(time.getIsBusy())time.setIsBusy(false);
            if(time.getTimeFrom().equals(startBreak)){
                time.setDate(time.getDate());
                time.setTimeFrom(fromDoctor.getSchedule().getStartBreak());
                time.setTimeTo(fromDoctor.getSchedule().getStartBreak().plusHours(hour).plusMinutes(interval));
                time.setIsBusy(false);
                time.setSchedule(schedule);
//                scheduleDateAndTimeRepository.save(time);
            }
        }
        schedule.setStartBreak(startBreak);
        schedule.setEndBreak(endBreak);
        toDoctor.setSchedule(schedule);
        scheduleRepository.save(schedule);
        doctorRepository.save(toDoctor);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added!")
                .build();
    }
}
