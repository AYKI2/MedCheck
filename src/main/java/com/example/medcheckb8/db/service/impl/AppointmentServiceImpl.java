package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.appointment.AddAppointmentRequest;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.dto.response.appointment.AppointmentDoctorResponse;
import com.example.medcheckb8.db.dto.response.appointment.AddAppointmentResponse;
import com.example.medcheckb8.db.dto.response.appointment.GetAllAppointmentResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
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
import com.example.medcheckb8.db.utill.TranslateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
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
    private final TranslateUtil translate;
    private static final Logger logger = Logger.getLogger(Appointment.class.getName());

    @Override
    public AddAppointmentResponse save(AddAppointmentRequest request) {
        try {
            logger.info("Saving appointment...");
            Account currentUser = jwtService.getAccountInToken();
            User user = userRepository.findByAccountId(currentUser.getId())
                    .orElseThrow(() -> new NotFountException("User not found!"));
            Doctor doctor = doctorRepository.findById(request.doctorId())
                    .orElseThrow(() -> new NotFountException("Doctor with id: " + request.doctorId() + " not found!"));
            Department department = departmentRepository.findByName(Detachment.valueOf(request.department()))
                    .orElseThrow(() -> new NotFountException("Department with name: " + request.department() + " not found!"));
            if (!doctorRepository.existsDoctorByDepartmentAndId(department, request.doctorId())) {
                throw new BadRequestException("This specialist does not work in this department.");
            }
            Boolean booked = dateAndTimeRepository.booked(doctor.getSchedule().getId(), request.date().toLocalDate(), request.date().toLocalTime());
            if (booked != null && booked) {
                throw new AlreadyExistException("This time is busy!");
            } else if (booked == null) {
                throw new BadRequestException("This specialist is not working on this day or time! Working dates: from " + doctor.getSchedule().getDataOfStart() + " to " + doctor.getSchedule().getDataOfFinish());
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

            String date = appointment.getDateOfVisit().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")) + ", "
                    + appointment.getDateOfVisit().getDayOfMonth() + " - "
                    + appointment.getDateOfVisit().getMonth().getDisplayName(TextStyle.FULL, new Locale("ru"));

            String subject = "Medcheck : Оповещение о записи";
            Context context = new Context();
            context.setVariable("title", String.format("Здравствуйте, %s!", appointment.getUser().getFirstName()));
            context.setVariable("department", translate.translateMethod(department.getName().name().toLowerCase(), "ru", "en"));
            context.setVariable("doctor", doctor.getLastName() + " " + doctor.getFirstName());
            context.setVariable("date", date);
            context.setVariable("time", appointment.getDateOfVisit().toLocalTime());

            context.setVariable("status", translate.translateMethod(appointment.getStatus().name().toLowerCase(), "ru", "en"));
            context.setVariable("now", LocalDate.now(ZoneId.of(request.zoneId())));
            context.setVariable("patient", appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName());
            context.setVariable("phoneNumber", appointment.getUser().getPhoneNumber());

            context.setVariable("link", request.link());

            String htmlContent = templateEngine.process("emailMessage.html", context);
            emailSenderService.sendEmail(request.email(), subject, htmlContent);
            logger.info("Saving appointment...");
            return AddAppointmentResponse.builder()
                    .response(AppointmentDoctorResponse.builder()
                            .id(doctor.getId())
                            .appointmentId(appointment.getId())
                            .fullName(doctor.getLastName() + " " + doctor.getFirstName())
                            .image(doctor.getImage())
                            .position(doctor.getPosition())
                            .build())
                    .dateAndTime(request.date())
                    .build();
        } catch (NotFountException e) {
            logger.log(Level.SEVERE, "Error saving appointment: " + e.getMessage(), e);
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<GetAllAppointmentResponse> getAll(String keyWord) {
        try {
            logger.info("Retrieving all appointments...");
            List<Appointment> all;
            if (keyWord != null) all = repository.findAll(keyWord);
            else all = repository.findAll();
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
                        .department(translate.translateMethod(appointment.getDepartment().getName().name().toLowerCase(), "ru", "en"))
                        .specialist(appointment.getDoctor().getLastName() + " " + appointment.getDoctor().getFirstName())
                        .localDate(appointment.getDateOfVisit().toLocalDate())
                        .localTime(appointment.getDateOfVisit().toLocalTime())
                        .status(status)
                        .build());
            }
            logger.info("Retrieved all appointments successfully");
            return response;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving appointments: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public SimpleResponse canceled(Long id) {
        try {
            logger.info("Canceling appointment with ID: " + id);
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
                logger.warning("Request to cancel appointment with ID: " + id + " not completed due to unauthorized user");
                return SimpleResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Request not completed!")
                        .build();
            }
        } catch (NotFountException e) {
            logger.log(Level.SEVERE, "Error canceling appointment with ID: " + id + ": " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public SimpleResponse delete(List<Long> appointments) {
        try {
            logger.info("Deleting appointments: " + appointments);
            User user = userRepository.findByAccountId(jwtService.getAccountInToken().getId())
                    .orElseThrow(() -> new NotFountException("User not found!"));
            if (appointments == null || appointments.isEmpty() && user.getAccount().getRole() == Role.PATIENT) {
                repository.deleteAll(user.getAppointments());
                user.getAppointments().clear();
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully cleared!")
                        .build();
            } else if (user.getAccount().getRole() == Role.ADMIN && appointments.size() == repository.count()) {
                logger.info("Deleting all appointments as an admin");
                repository.deleteAll();
                logger.info("All appointments deleted successfully");

                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully cleared!")
                        .build();
            } else {
                for (Long appointment : appointments) {
                    logger.info("Deleting appointment with ID: " + appointment);
                    repository.deleteById(appointment);
                    user.getAppointments().remove(repository.findById(appointment)
                            .orElseThrow(() -> new NotFountException("Appointment not found!")));
                    logger.info("Appointment deleted successfully with ID: " + appointment);
                }
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully deleted!")
                        .build();
            }
        } catch (NotFountException e) {
            logger.log(Level.SEVERE, "Error deleting appointments: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AppointmentResponse> getUserAppointments() {
        try {
            Account account = jwtService.getAccountInToken();
            logger.info("Retrieving appointments for user with email: {}" + account.getEmail());
            List<AppointmentResponse> appointments = appointmentRepository.getUserAppointments(account.getEmail());
            logger.severe("Retrieved {} appointments for user with email: {}" + appointments.size() + account.getEmail());
            return appointments;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving user appointments: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}