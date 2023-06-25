package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.dto.response.PaginationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

import java.util.List;

public interface ApplicationService {
    SimpleResponse addApplication(ApplicationRequest request);

    PaginationResponse<ApplicationResponse> getAllApplication(String word, int page, int size);

    SimpleResponse deleteByIdApplication(List<Long> id);

    ApplicationResponse findById(Long id);
}
