package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Result;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.ResultRepository;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(Result.class.getName());

    @Override
    public SimpleResponse addResult(ResultRequest request) {
        try {
            User user = userRepository.findById(request.patientId())
                    .orElseThrow(() -> new NotFountException(
                            String.format("Patient with id: %d doesn't exist.", request.patientId())));

            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new NotFountException(
                            String.format("Department with id: %d doesn't exist.", request.departmentId())));

            Result result = Result.builder()
                    .department(department)
                    .dateOfIssue(request.dateOfIssue())
                    .orderNumber(uniquenessCheckOrderNumber())
                    .file(request.file())
                    .user(user)
                    .build();

            user.addResult(result);
            resultRepository.save(result);
            logger.log(Level.INFO, String.format("Result with patient full name: %s successfully added.",
                    (user.getFirstName() + " " + user.getLastName())));

            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("Result with patient full name: %s successfully added.",
                            (user.getFirstName() + " " + user.getLastName())))
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add result", e);
            throw e;
        }

    }

    @Override
    public ResultResponse getResult(String orderNumber) {
        ResultResponse result = resultRepository.getResultByOrderNumber(orderNumber)
                .orElseThrow(() -> new NotFountException(
                        String.format("Result with order number: %s doesn't exist.", orderNumber)
                ));
        logger.log(Level.INFO, "Result retrieved: OrderNumber={0}", orderNumber);
        return result;
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
