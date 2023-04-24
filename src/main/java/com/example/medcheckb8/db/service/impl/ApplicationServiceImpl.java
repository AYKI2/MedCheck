package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.entities.Application;
import com.example.medcheckb8.db.service.ApplicationService;
import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<ApplicationResponse> getAllApplication(String word) {
        if (word == null) {
            return repository.getAllApplication();
        }
        return repository.globalSearch(word);
    }
}