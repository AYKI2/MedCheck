package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorScheduleRequest;
import com.example.medcheckb8.db.dto.request.appointment.InstallByTemplate;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.entities.Schedule;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;

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
import java.time.format.TextStyle;
import java.util.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorRepository doctorRepository;
    private final com.example.medcheckb8.db.repository.ScheduleRepository scheduleRepository;

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

        boolean contains = department.getDoctors().contains(doctor);
        if(!contains) throw new BadRequestException("Doctor with " +request.doctorId()+ " id does not work in this department.");

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
        if(doctor.getSchedule() == null) {
            Schedule schedule = Schedule.builder()
                    .dataOfStart(request.startDate())
                    .dataOfFinish(request.endDate())
                    .intervalOfHours(request.interval())
                    .startBreak(LocalTime.parse(request.startBreak()))
                    .endBreak(LocalTime.parse(request.endBreak()))
                    .repeatDay(repeatDays)
                    .doctor(doctor)
                    .department(department)
                    .build();
            doctor.setSchedule(schedule);
        }else {
            Schedule schedule = doctor.getSchedule();
            schedule.setDataOfStart(request.startDate());
            schedule.setDataOfFinish(request.endDate());
            schedule.setIntervalOfHours(request.interval());
            schedule.setStartBreak(LocalTime.parse(request.startBreak()));
            schedule.setEndBreak(LocalTime.parse(request.endBreak()));
            schedule.setRepeatDay(repeatDays);
        }

        List<ScheduleDateAndTime> dateAndTimes = new ArrayList<>();
        ScheduleDateAndTime build = new ScheduleDateAndTime();
        LocalDate date = request.startDate();

        while (request.endDate().isAfter(date) || request.endDate().isEqual(date)) {
            LocalTime start = LocalTime.parse(request.startTime());
            int interval = request.interval();
            int hour = 0;
            while(interval >= 60){
                interval -= 60;
                hour++;
            }
            LocalTime end = start.plusHours(hour).plusMinutes(interval);
            LocalTime endTime = LocalTime.parse(request.endTime());
            String name = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
            Boolean aBoolean = repeatDays.get(Repeat.valueOf(name));
            boolean isBefore = true;
            if (aBoolean) {
                while (!start.equals(endTime) && !start.isAfter(endTime)) {
                    if (end.isAfter(endTime)) end = endTime;
                    if(!start.equals(doctor.getSchedule().getStartBreak())) {
                        build = ScheduleDateAndTime.builder()
                                .date(date)
                                .timeFrom(start)
                                .timeTo(end)
                                .isBusy(false)
                                .schedule(doctor.getSchedule())
                                .build();
                        start = end;
                        end = end.plusHours(hour).plusMinutes(interval);
                        if(end.isAfter(doctor.getSchedule().getStartBreak()) && isBefore) {
                            dateAndTimes.add(build);
                            isBefore = false;
                            LocalTime scheduleEnd = doctor.getSchedule().getEndBreak().plusHours(hour).plusMinutes(interval);
                            build = ScheduleDateAndTime.builder()
                                    .date(date)
                                    .timeFrom(doctor.getSchedule().getEndBreak())
                                    .timeTo(scheduleEnd)
                                    .isBusy(false)
                                    .schedule(doctor.getSchedule())
                                    .build();
                            start = scheduleEnd;
                            end = start.plusHours(hour).plusMinutes(interval);
                        }
                    }
                    dateAndTimes.add(build);
                }
            }
            date = date.plusDays(1L);
        }
        doctor.getSchedule().setDateAndTimes(dateAndTimes);
        doctorRepository.save(doctor);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Schedule successfully saved!")
                .build();
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
