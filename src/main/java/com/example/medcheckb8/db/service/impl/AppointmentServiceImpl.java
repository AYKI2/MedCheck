package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.appointment.AddAppointmentRequest;
import com.example.medcheckb8.db.dto.response.*;
import com.example.medcheckb8.db.dto.response.appointment.AppointmentDoctorResponse;
import com.example.medcheckb8.db.dto.response.appointment.AddAppointmentResponse;
import com.example.medcheckb8.db.dto.response.appointment.GetAllAppointmentResponse;
import com.example.medcheckb8.db.entities.*;
import com.example.medcheckb8.db.enums.Detachment;
import com.example.medcheckb8.db.enums.Role;
import com.example.medcheckb8.db.enums.Status;
import com.example.medcheckb8.db.exceptions.AlreadyExistException;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.*;
import com.example.medcheckb8.db.service.AppointmentService;
import com.example.medcheckb8.db.service.EmailSenderService;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.entities.Appointment;
import com.example.medcheckb8.db.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleDateAndTimeRepository dateAndTimeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final AppointmentRepository repository;
    private final AppointmentRepository appointmentRepository;
    private static final Logger logger = Logger.getLogger(Appointment.class.getName());

    @Override
    public AddAppointmentResponse save(AddAppointmentRequest request) {
        Account currentUser = jwtService.getAccountInToken();
        User user = userRepository.findByAccountId(currentUser.getId())
                .orElseThrow(() -> new NotFountException("Пользователь не найден!"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFountException("Доктор с идентификатором: " + request.doctorId() + " не найден!"));
        Department department = departmentRepository.findByName(Detachment.valueOf(request.department()))
                .orElseThrow(() -> new NotFountException("Отделение с названием: " + request.department() + " не найден!"));
        if (!doctorRepository.existsDoctorByDepartmentAndId(department, request.doctorId())) {
            throw new BadRequestException("Этот специалист не работает в данном отделении.");
        }
        Boolean booked = dateAndTimeRepository.booked(doctor.getSchedule().getId(), request.date(), LocalTime.parse(request.time()));
        if (booked != null && booked) {
            throw new AlreadyExistException("Это время занято!");
        } else if (booked == null) {
            throw new BadRequestException("Этот специалист не работает в этот день или в это время! Рабочие даты: с" + doctor.getSchedule().getDataOfStart() + " to " + doctor.getSchedule().getDataOfFinish());
        }
        Appointment appointment = Appointment.builder()
                .fullName(request.fullName())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .status(Status.CONFIRMED)
                .dateOfVisit(request.date())
                .timeOfVisit(LocalTime.parse(request.time()))
                .user(user)
                .doctor(doctor)
                .department(department)
                .build();
        ScheduleDateAndTime dateAndTime = dateAndTimeRepository.findByDateAndTime(doctor.getId(), request.date(), LocalTime.parse(request.time()));
        dateAndTime.setIsBusy(true);
        doctor.getAppointments().add(appointment);
        user.getAppointments().add(appointment);
        repository.save(appointment);

        String date = appointment.getDateOfVisit().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")) +", "
                +appointment.getDateOfVisit().getDayOfMonth() +" - "
                +appointment.getDateOfVisit().getMonth().getDisplayName(TextStyle.FULL,new Locale("ru"));

        String subject = "Medcheck : Оповещение о записи";
        Context context = new Context();
        context.setVariable("title", String.format("Здравствуйте, %s!", appointment.getUser().getFirstName()));
        context.setVariable("department", department.getName().getTranslate());
        context.setVariable("doctor", doctor.getLastName() + " " + doctor.getFirstName());
        context.setVariable("date", date);
        context.setVariable("time", appointment.getTimeOfVisit());

        context.setVariable("status", appointment.getStatus().name().toLowerCase());
        context.setVariable("now", LocalDate.now(ZoneId.of(request.zoneId())));
        context.setVariable("patient", appointment.getFullName());
        context.setVariable("phoneNumber", appointment.getPhoneNumber());

        context.setVariable("link", request.link());

        String htmlContent = templateEngine.process("emailMessage.html", context);
        emailSenderService.sendEmail(request.email(), subject, htmlContent);

        return AddAppointmentResponse.builder()
                .response(AppointmentDoctorResponse.builder()
                        .id(doctor.getId())
                        .appointmentId(appointment.getId())
                        .fullName(doctor.getLastName() + " " + doctor.getFirstName())
                        .image(doctor.getImage())
                        .position(doctor.getPosition())
                        .build())
                .date(request.date())
                .dayOfWeek(request.date().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")))
                .timeFrom(String.format(String.valueOf(DateTimeFormatter.ofPattern("HH:mm"))))
                .timeTo(String.format(String.valueOf(DateTimeFormatter.ofPattern("HH:mm"))))
                .build();
    }

    @Override
    public PaginationResponse<GetAllAppointmentResponse> getAll(String keyWord, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Appointment> all = repository.findAll(keyWord, pageable);
        List<GetAllAppointmentResponse> appointments = new ArrayList<>();
        boolean status = false;
        for (Appointment appointment : all) {
            if (appointment.getStatus().equals(Status.CONFIRMED)) {
                status = false;
            } else if (appointment.getStatus().equals(Status.COMPLETED)) {
                status = true;
            }
            appointments.add(GetAllAppointmentResponse.builder()
                    .appointmentId(appointment.getId())
                    .patientId(appointment.getUser().getId())
                    .doctorId(appointment.getDoctor().getId())
                    .fullName(appointment.getFullName())
                    .phoneNumber(appointment.getPhoneNumber())
                    .email(appointment.getEmail())
                    .department(appointment.getDepartment().getName().getTranslate())
                    .specialist(appointment.getDoctor().getLastName() + " " + appointment.getDoctor().getFirstName())
                    .localDate(appointment.getDateOfVisit())
                    .localTime(appointment.getTimeOfVisit())
                    .status(status)
                    .build());
        }
        PaginationResponse<GetAllAppointmentResponse> response = new PaginationResponse<>();
        response.setResponses(appointments);
        response.setCurrentPage(pageable.getPageNumber() + 1);
        response.setPageSize(appointments.size());
        return response;
    }

    @Transactional
    @Override
    public SimpleResponse canceled(Long id) {
        User user = userRepository.findByAccountId(jwtService.getAccountInToken().getId())
                .orElseThrow(() -> new NotFountException("Пользователь не найден!"));
        Appointment appointment = repository.findById(id).orElseThrow(() ->
                new NotFountException("Запись с идентификатором: " + id + " не найдена!"));
        if (appointment.getUser().getId().equals(user.getId())) {
            ScheduleDateAndTime dateAndTime = dateAndTimeRepository.findByDateAndTime(appointment.getDoctor().getId(),
                    appointment.getDateOfVisit(),
                    appointment.getTimeOfVisit());
            dateAndTime.setIsBusy(false);
            appointment.setStatus(Status.CANCELED);
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Успешно отменено!")
                    .build();
        } else {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Запрос не выполнен!")
                    .build();
        }
    }

    @Transactional
    @Override
    public SimpleResponse delete(List<Long> appointments) {
        User user = userRepository.findByAccountId(jwtService.getAccountInToken().getId())
                .orElseThrow(() -> new NotFountException("Пользователь не найден!"));
        if (appointments == null || appointments.isEmpty() && user.getAccount().getRole() == Role.PATIENT) {
            repository.deleteAll(user.getAppointments());
            user.getAppointments().clear();
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Успешно очищено!")
                    .build();
        } else if (user.getAccount().getRole() == Role.ADMIN && appointments.size() == repository.count()) {
            repository.deleteAll();
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Успешно очищено!")
                    .build();
        } else if(user.getAccount().getRole() == Role.ADMIN) {
            for (Long appointment : appointments) {
                Appointment appointmentObject = repository.findById(appointment)
                        .orElseThrow(()-> new NotFountException(String.format("Запись с id: %d не найдено!", appointment)));
                user.getAppointments().remove(appointmentObject);
                repository.deleteById(appointment);
            }
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Успешно удалено!")
                    .build();
        }
        return SimpleResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Ошибка удаления!")
                .build();
    }
        
    @Override
    public List<AppointmentResponse> getUserAppointments() {
        Account account = jwtService.getAccountInToken();
        logger.info("Получение записей для пользователя с email: {}" + account.getEmail());
        List<AppointmentResponse> appointments = appointmentRepository.getUserAppointments(account.getEmail());
        logger.severe("Получено {} записей для пользователя с email: {}" + appointments.size() + account.getEmail());
        return appointments;
    }

    @Override
    public AppointmentResponseId getUserAppointmentById(Long id) {
        return appointmentRepository.findByPatientId(id);
    }
}