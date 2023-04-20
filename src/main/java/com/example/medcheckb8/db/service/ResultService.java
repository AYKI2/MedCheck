package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

public interface ResultService {
    SimpleResponse addResult(Long patientId, ResultRequest request);
}
