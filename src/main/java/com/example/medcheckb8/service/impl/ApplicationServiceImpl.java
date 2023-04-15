package com.example.medcheckb8.service.impl;

import com.example.medcheckb8.db.entities.Application;
import com.example.medcheckb8.dto.request.ApplicationRequest;
import com.example.medcheckb8.dto.response.SimpleResponse;
import com.example.medcheckb8.repository.ApplicationRepository;
import com.example.medcheckb8.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;

    @Override
    public SimpleResponse addApplication(ApplicationRequest request) {
        Application application = new Application();
        application.setName(request.name());
        application.setDate(LocalDate.now());
        application.setPhoneNumber(request.phoneNumber());
        application.setProcessed(false);
        repository.save(application);
        return new SimpleResponse(HttpStatus.OK, String.format("Successfully  %s saved!", request.name()));
    }
}
