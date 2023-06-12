package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.UserResultResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Result;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.ResultRepository;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.EmailSenderService;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final EmailSenderService emailSenderService;
    private static final Logger logger = Logger.getLogger(Result.class.getName());

    @Override
    public UserResultResponse addResult(ResultRequest request) {
        try {
            User user = userRepository.findById(request.patientId())
                    .orElseThrow(()->new NotFountException(String.format("Пациент с ID: %d не найден!",request.patientId())));
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(()->new NotFountException(String.format("Отделение с ID: %d не найдено!",request.departmentId())));

            String ordNum = uniquenessCheckOrderNumber();
            LocalDate date = LocalDate.from(request.date());
            LocalTime time = LocalTime.parse(LocalTime.from(request.date()).format(DateTimeFormatter.ofPattern("HH:mm")));

            Result result = Result.builder()
                    .department(department)
                    .dateOfIssue(date)
                    .timeOfIssue(time)
                    .orderNumber(ordNum)
                    .file(request.file())
                    .user(user)
                    .build();

            user.addResult(result);
            resultRepository.save(result);
            logger.log(Level.INFO, String.format("Результат с полным именем пациента: %s успешно добавлен.",
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
                    .name(department.getName().name().toLowerCase())
                    .date(date)
                    .time(time)
                    .orderNumber(ordNum)
                    .file(request.file())
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении результата", e);
            throw e;
        }

    }

    @Override
    public ResultResponse getResult(String orderNumber) {
        return resultRepository.getResultByOrderNumber(orderNumber)
                .orElseThrow(()-> new NotFountException(
                        String.format("Результат с номером заказа: %s не существует.",orderNumber)
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
        logger.info(String.format("Сгенерирован номер заказа: %s", orderNumber));
        return orderNumber;
    }

    private String uniquenessCheckOrderNumber() {
        List<Result> all = resultRepository.findAll();
        String s = generateOrderNumber();
        for (Result result : all) {
            if (s.equals(result.getOrderNumber())) {
                s = generateOrderNumber();
                logger.info("Сгенерирован новый номер заказа {} из-за дублирования с ID результата: {}" + s + result.getId());
            } else {
                return s;
            }
        }
        return s;
    }

    @Override
    public List<UserResultResponse> getResultByUserId(Long patientId) {
        return resultRepository.getResultByUserId(patientId);
    }
}
