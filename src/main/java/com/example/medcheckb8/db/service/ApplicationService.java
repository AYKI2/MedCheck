package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
public interface ApplicationService {
    SimpleResponse addApplication(ApplicationRequest request);
}
