package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.AppointmentRequest;
import com.example.medcheckb8.db.dto.response.AppointmentDoctorResponse;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.entities.*;
import com.example.medcheckb8.db.enums.Detachment;
import com.example.medcheckb8.db.enums.Status;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.AppointmentService;
import com.example.medcheckb8.db.service.EmailSenderService;
import org.thymeleaf.TemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;

    @Override
    public AppointmentResponse save(AppointmentRequest request) {
        Account currentUser = jwtService.getAccountInToken();
        User user = userRepository.findByAccountId(currentUser.getId())
                .orElseThrow(() -> new NotFountException("User not found!"));
        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFountException("Doctor with id: " + request.doctorId() + " not found!"));
        Department department = departmentRepository.findByName(Detachment.valueOf(request.department()))
                .orElseThrow(() -> new NotFountException("Department with name: " + request.department() + " not found!"));
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
        doctor.getAppointments().add(appointment);
        user.getAppointments().add(appointment);

        String subject = "Medcheck : Оповещение о записи";
        String resetPasswordLink = "http://localhost:2023/api/auth/signIn";

        Context context = new Context();
        context.setVariable("title", "Здравствуйте "+ user.getFirstName()+"!");
        context.setVariable("message", String.format("Вы записались на прием к специалисту %s на %s, %d - %s, %d:%d",
                doctor.getLastName() +" "+ doctor.getFirstName(), request.date().getDayOfWeek().name(),
                request.date().getDayOfYear(),request.date().getMonth().name(),
                request.date().getHour(), request.date().getMinute()));
        context.setVariable("link", resetPasswordLink);
        context.setVariable("linkTitle", "Ваша запись");

        String htmlContent = templateEngine.process("emailMessage.html", context);
        emailSenderService.sendEmail(request.email(), subject, htmlContent);

        return AppointmentResponse.builder()
                .response(AppointmentDoctorResponse.builder()
                        .fullName(doctor.getLastName() + " " + doctor.getFirstName())
                        .image(doctor.getImage())
                        .position(doctor.getPosition())
                        .build())
                .dateAndTime(request.date())
                .build();
    }
}
