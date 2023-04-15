package com.example.medcheckb8.api;

import com.example.medcheckb8.dto.request.ApplicationRequest;
import com.example.medcheckb8.dto.response.SimpleResponse;
import com.example.medcheckb8.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationAPI {
    private final ApplicationService service;

    @PostMapping("/save")
    public SimpleResponse addApplication(@RequestBody @Valid ApplicationRequest request) {
        return service.addApplication(request);
    }

}
