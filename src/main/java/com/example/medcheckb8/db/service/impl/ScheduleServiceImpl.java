package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorScheduleRequest;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.entities.Schedule;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.repository.custom.ScheduleRepository;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final com.example.medcheckb8.db.repository.ScheduleRepository scheduleRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<ScheduleResponse> getAllSchedule(String word,
                                                 LocalDate startDate,
                                                 LocalDate endDate) {
        return repository.getAll(word, startDate, endDate);
    }

    @Override
    public SimpleResponse save(DoctorScheduleRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NotFountException("Department with id: " + request.departmentId() + " not found!"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFountException("Doctor with id: " + request.doctorId() + " not found!"));

        Map<Repeat, Boolean> repeatDays = new HashMap<>();
        for (String day : request.repeatDays().keySet()) {
            switch (day) {
                case "пн" -> repeatDays.put(Repeat.MONDAY, request.repeatDays().get(day));
                case "вт" -> repeatDays.put(Repeat.TUESDAY, request.repeatDays().get(day));
                case "ср" -> repeatDays.put(Repeat.WEDNESDAY, request.repeatDays().get(day));
                case "чт" -> repeatDays.put(Repeat.THURSDAY, request.repeatDays().get(day));
                case "пт" -> repeatDays.put(Repeat.FRIDAY, request.repeatDays().get(day));
                case "сб" -> repeatDays.put(Repeat.SATURDAY, request.repeatDays().get(day));
                case "вс" -> repeatDays.put(Repeat.SUNDAY, request.repeatDays().get(day));
            }
        }
        List<ScheduleDateAndTime> dateAndTimes = new ArrayList<>();
        ScheduleDateAndTime build;
        LocalDate date = request.startDate();

        Schedule schedule = Schedule.builder()
                .dataOfStart(request.startDate())
                .dataOfFinish(request.endDate())
                .intervalOfHours(request.interval())
                .repeatDay(repeatDays)
                .doctor(doctor)
                .department(department)
                .build();
        doctor.setSchedule(schedule);

        while (request.endDate().isAfter(date) || request.endDate().isEqual(date)) {
            LocalTime start = LocalTime.parse(request.startTime());
            LocalTime end = LocalTime.parse(request.startTime()).plusMinutes(request.interval());
            String name = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
            Boolean aBoolean = repeatDays.get(Repeat.valueOf(name));
            if (aBoolean) {
                while (!start.equals(LocalTime.parse(request.endTime()))) {
                    if (!start.equals(LocalTime.parse(request.startBreak())) && !end.equals(LocalTime.parse(request.endBreak()))) {
                        build = ScheduleDateAndTime.builder()
                                .date(date)
                                .timeFrom(start)
                                .timeTo(end)
                                .isBusy(false)
                                .schedule(doctor.getSchedule())
                                .build();
                        start = end;
                        end = end.plusMinutes(request.interval());
                    } else {
                        build = ScheduleDateAndTime.builder()
                                .date(date)
                                .timeFrom(LocalTime.parse(request.startBreak()))
                                .timeTo(LocalTime.parse(request.endBreak()))
                                .isBusy(true)
                                .schedule(doctor.getSchedule())
                                .build();
                        start = LocalTime.parse(request.endBreak());
                        end = LocalTime.parse(request.endBreak()).plusMinutes(request.interval());
                    }
                    dateAndTimes.add(build);
                }
            }
            date = date.plusDays(1L);
        }
        doctor.getSchedule().setDateAndTimes(dateAndTimes);
        scheduleRepository.save(schedule);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Schedule successfully saved!")
                .build();
    }
}
