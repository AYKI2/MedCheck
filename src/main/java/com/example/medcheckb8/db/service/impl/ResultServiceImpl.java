package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.UserResultResponse;
import com.example.medcheckb8.db.entities.Appointment;
import com.example.medcheckb8.db.entities.Result;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.AppointmentRepository;
import com.example.medcheckb8.db.repository.ResultRepository;
import com.example.medcheckb8.db.service.EmailSenderService;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final TemplateEngine templateEngine;
    private final EmailSenderService emailSenderService;
    private static final Logger logger = Logger.getLogger(Result.class.getName());
    private final AppointmentRepository appointmentRepository;

    @Override
    public UserResultResponse addResult(ResultRequest request) {
        try {
            Appointment appointment = appointmentRepository.findById(request.appointmentId())
                    .orElseThrow(()->new NotFountException(String.format("Appointment with id: %d not found!", request.appointmentId())));

            String ordNum = uniquenessCheckOrderNumber();
            LocalDate now = LocalDate.now(ZoneId.of(request.zoneId()));

            LocalDate date = LocalDate.from(now);
            LocalTime time = LocalTime.from(now);

            Result result = Result.builder()
                    .department(appointment.getDepartment())
                    .dateOfIssue(date)
                    .timeOfIssue(time)
                    .orderNumber(ordNum)
                    .file(request.file())
                    .user(appointment.getUser())
                    .build();

            User user = appointment.getUser();
            appointment.setResult(result);
            user.addResult(result);
            resultRepository.save(result);
            logger.log(Level.INFO, String.format("Result with patient full name: %s successfully added.",
                    (user.getFirstName() + " " + user.getLastName())));

            String subject = "Medcheck : Оповещение о результате";
            Context context = new Context();
            context.setVariable("title", String.format("Здравствуйте, %s %s!", user.getFirstName(), user.getLastName()));
            context.setVariable("secondMessage", String.format("Ваш номер результата: %s", ordNum));

            String html  = templateEngine.process("resultEmail.html", context);
            emailSenderService.sendEmail(user.getAccount().getEmail(), subject, html);

            return UserResultResponse.builder()
                    .resultId(result.getId())
                    .patientId(user.getId())
                    .name(appointment.getDepartment().getName())
                    .date(date)
                    .time(time)
                    .orderNumber(ordNum)
                    .file(request.file())
                    .build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add result", e);
            throw e;
        }

    }

    @Override
    public ResultResponse getResult(String orderNumber) {
        return resultRepository.getResultByOrderNumber(orderNumber)
                .orElseThrow(()-> new NotFountException(
                        String.format("Result with order number: %s doesn't exist.",orderNumber)
                ));
    }

    private String generateOrderNumber() {
        Random random = new Random();
        int length = 18;
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        String orderNumber = sb.toString();
        logger.info(String.format("Generated order number: %s", orderNumber));
        return orderNumber;
    }

    private String uniquenessCheckOrderNumber() {
        List<Result> all = resultRepository.findAll();
        String s = generateOrderNumber();
        for (Result result : all) {
            if (s.equals(result.getOrderNumber())) {
                s = generateOrderNumber();
                logger.info("Generated a new order number {} due to duplication with result id: {}" + s + result.getId());
            } else {
                return s;
            }
        }
        return s;
    }
}
