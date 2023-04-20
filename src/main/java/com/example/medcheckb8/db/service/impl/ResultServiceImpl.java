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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    @Override
    public SimpleResponse addResult(Long patientId, ResultRequest request) {
        User user = userRepository.findById(request.departmentId())
                .orElseThrow(() -> new NotFountException(
                        String.format("Patient with id: %d doesn't exist.", patientId)));

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NotFountException(
                        String.format("Department with id: %d doesn't exist.", request.departmentId())));

        Result result = Result.builder()
                .department(department)
                .dateOfIssue(request.dateOfIssue())
                .file(request.file())
                .build();

        user.addResult(result);
        resultRepository.save(result);

        return SimpleResponse.builder()
                .message(String.format("Result with patient full name: %s successfully added.",
                        (user.getFirstName() + " " + user.getLastName())))
                .build();
    }
}
