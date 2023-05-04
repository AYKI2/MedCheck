package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.AppointmentRequest;
import com.example.medcheckb8.db.dto.response.AppointmentDoctorResponse;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.dto.response.GetAllAppointmentResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.*;
import com.example.medcheckb8.db.enums.Detachment;
import com.example.medcheckb8.db.enums.Role;
import com.example.medcheckb8.db.enums.Status;
import com.example.medcheckb8.db.exceptions.AlreadyExistException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.*;
import com.example.medcheckb8.db.service.AppointmentService;
import com.example.medcheckb8.db.service.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleDateAndTimeRepository dateAndTimeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final AppointmentRepository repository;

    @Override
    public AppointmentResponse save(AppointmentRequest request) {
        Account currentUser = jwtService.getAccountInToken();
        User user = userRepository.findByAccountId(currentUser.getId())
                .orElseThrow(() -> new NotFountException("User not found!"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFountException("Doctor with id: " + request.doctorId() + " not found!"));
        Department department = departmentRepository.findByName(Detachment.valueOf(request.department()))
                .orElseThrow(() -> new NotFountException("Department with name: " + request.department() + " not found!"));
        if (!doctorRepository.existsDoctorByDepartmentAndId(department, request.doctorId())) {
            throw new NotFountException("This specialist does not work in this department.");
        }
        Boolean booked = dateAndTimeRepository.booked(doctor.getSchedule().getId(), request.date().toLocalDate(), request.date().toLocalTime());
        if (booked != null && booked) {
            throw new AlreadyExistException("This time is busy!");
        } else if (booked == null) {
            throw new NotFountException("This specialist is not working on this day(" + doctor.getSchedule().getDataOfStart() + "-" + doctor.getSchedule().getDataOfFinish() +
                    ") or time");
        }
        Appointment appointment = Appointment.builder()
                .fullName(request.fullName())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .status(Status.CONFIRMED)
                .dateOfVisit(request.date())
                .user(user)
                .doctor(doctor)
                .department(department)
                .build();
        ScheduleDateAndTime dateAndTime = dateAndTimeRepository.findByDateAndTime(doctor.getId(), request.date().toLocalDate(), request.date().toLocalTime());
        dateAndTime.setIsBusy(true);
        doctor.getAppointments().add(appointment);
        user.getAppointments().add(appointment);
        repository.save(appointment);

        String subject = "Medcheck : Оповещение о записи";
        Context context = new Context();
        context.setVariable("title", "MEDCHECK");
        context.setVariable("firstMessage", String.format("Здравствуйте %s!", request.fullName()));
        context.setVariable("secondMessage", String.format("Вы успешно записались на прием к специалисту %s.", doctor.getLastName() + " " + doctor.getFirstName()));
        context.setVariable("thirdMessage", String.format("Ждем вас в %s, %d - %s, %d:%d",
                request.date().format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru"))),
                request.date().getDayOfMonth(),
                request.date().format(DateTimeFormatter.ofPattern("MMMM", new Locale("ru"))),
                request.date().getHour(), request.date().getMinute()));

        String htmlContent = templateEngine.process("emailMessage.html", context);
        emailSenderService.sendEmail(request.email(), subject, htmlContent);

        return AppointmentResponse.builder()
                .response(AppointmentDoctorResponse.builder()
                        .id(doctor.getId())
                        .appointmentId(appointment.getId())
                        .fullName(doctor.getLastName() + " " + doctor.getFirstName())
                        .image(doctor.getImage())
                        .position(doctor.getPosition())
                        .build())
                .dateAndTime(request.date())
                .build();
    }

    @Override
    public List<GetAllAppointmentResponse> getAll() {
        List<Appointment> all = repository.findAll();
        List<GetAllAppointmentResponse> response = new ArrayList<>();
        boolean status = false;
        for (Appointment appointment : all) {
            if (appointment.getStatus().equals(Status.CONFIRMED)) {
                status = false;
            } else if (appointment.getStatus().equals(Status.COMPLETED)) {
                status = true;
            }
            response.add(GetAllAppointmentResponse.builder()
                    .appointmentId(appointment.getId())
                    .patientId(appointment.getUser().getId())
                    .doctorId(appointment.getDoctor().getId())
                    .fullName(appointment.getFullName())
                    .phoneNumber(appointment.getPhoneNumber())
                    .email(appointment.getEmail())
                    .department(appointment.getDepartment().getName().name())
                    .specialist(appointment.getDoctor().getLastName() + " " + appointment.getDoctor().getFirstName())
                    .localDateTime(appointment.getDateOfVisit())
                    .status(status)
                    .build());
        }
        return response;
    }

    @Transactional
    @Override
    public SimpleResponse canceled(Long id) {
        User user = userRepository.findByAccountId(jwtService.getAccountInToken().getId())
                .orElseThrow(() -> new NotFountException("User not found!"));
        Appointment appointment = repository.findById(id).orElseThrow(() ->
                new NotFountException("Appointment with id: " + id + " not found!"));
        if (appointment.getUser().getId().equals(user.getId())) {
            ScheduleDateAndTime dateAndTime = dateAndTimeRepository.findByDateAndTime(appointment.getDoctor().getId(),
                    appointment.getDateOfVisit().toLocalDate(),
                    appointment.getDateOfVisit().toLocalTime());
            dateAndTime.setIsBusy(false);
            appointment.setStatus(Status.CANCELED);
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Successfully canceled!")
                    .build();
        } else {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Request not completed!")
                    .build();
        }
    }

    @Transactional
    @Override
    public SimpleResponse delete(List<Long> appointments) {
        User user = userRepository.findByAccountId(jwtService.getAccountInToken().getId())
                .orElseThrow(() -> new NotFountException("User not found!"));
        if (appointments.isEmpty() && user.getAccount().getRole() == Role.PATIENT) {
            repository.deleteAll(user.getAppointments());
            user.getAppointments().clear();
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Successfully cleared!")
                    .build();
        } else if (user.getAccount().getRole() == Role.ADMIN && appointments.size() == repository.count()) {
            //надо проверить правильно ли все работает
            //если админ удалит все записи удалятся ли они у пациентов
            repository.deleteAll();
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Successfully cleared!")
                    .build();
        } else {
            for (Long appointment : appointments) {
                repository.deleteById(appointment);
                user.getAppointments().remove(repository.findById(appointment)
                        .orElseThrow(() -> new NotFountException("Appointment not found!")));
            }
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Successfully deleted!")
                    .build();
        }
    }
}
