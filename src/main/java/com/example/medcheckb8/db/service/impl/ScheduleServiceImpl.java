package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorScheduleRequest;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.entities.Schedule;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import com.example.medcheckb8.db.enums.Detachment;
import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.exceptions.AlreadyExistException;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;

import com.example.medcheckb8.db.repository.custom.ScheduleRepository;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private static final Logger logger = Logger.getLogger(ScheduleService.class.getName());

    @Override
    public List<ScheduleResponse> getAllSchedule(String word,
                                                 LocalDate startDate,
                                                 LocalDate endDate) {
        return repository.getAll(word, startDate, endDate);
    }

    @Override
    public SimpleResponse save(DoctorScheduleRequest request) {
        Detachment detachment = Detachment.ALLERGOLOGY;
        for (Detachment value : Detachment.values()) {
            if(value.getTranslate().equals(request.department())){detachment = value; break;}
        }
        Department department = departmentRepository.findByName(detachment)
                .orElseThrow(() -> new NotFountException("Отделение с названием: " + request.department() + " не найдено!"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFountException("Врач с ID : " + request.doctorId() + " не найдено!"));

        boolean contains = department.getDoctors().contains(doctor);
        if(!contains) throw new BadRequestException("Врач с ID " +request.doctorId()+ " не работает в этом отделении.");
        if(doctor.getSchedule() != null) {throw new AlreadyExistException("Врач с ID: "+ request.doctorId() +" уже имеет расписание.");}

        if(request.startDate().isAfter(request.endDate()) ||
                LocalTime.parse(request.startBreak()).isAfter(LocalTime.parse(request.endBreak())) ||
                LocalTime.parse(request.startTime()).isAfter(LocalTime.parse(request.endTime()))) throw new BadRequestException("Неверно введены данные!");

        Map<Repeat, Boolean> repeatDays = new HashMap<>();
        for (String day : request.repeatDays().keySet()) {
            switch (day.toLowerCase()) {
                case "пн" -> repeatDays.put(Repeat.MONDAY, request.repeatDays().get(day));
                case "вт" -> repeatDays.put(Repeat.TUESDAY, request.repeatDays().get(day));
                case "ср" -> repeatDays.put(Repeat.WEDNESDAY, request.repeatDays().get(day));
                case "чт" -> repeatDays.put(Repeat.THURSDAY, request.repeatDays().get(day));
                case "пт" -> repeatDays.put(Repeat.FRIDAY, request.repeatDays().get(day));
                case "сб" -> repeatDays.put(Repeat.SATURDAY, request.repeatDays().get(day));
                case "вс" -> repeatDays.put(Repeat.SUNDAY, request.repeatDays().get(day));
            }
        }
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
        logger.log(Level.INFO, "Расписание сохранено: ID врача={0}, ID отделения={1}",
                new Object[]{doctor.getId(), department.getId()});


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Расписание успешно сохранено!")
                .build();
    }
}
