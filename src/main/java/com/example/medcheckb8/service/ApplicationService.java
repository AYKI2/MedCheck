package com.example.medcheckb8.service;

import com.example.medcheckb8.dto.request.ApplicationRequest;
import com.example.medcheckb8.dto.response.SimpleResponse;

public interface ApplicationService {
    SimpleResponse addApplication(ApplicationRequest request);
}
