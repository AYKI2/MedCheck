package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.UserResultResponse;

import java.util.List;

public interface ResultService {
    UserResultResponse addResult(ResultRequest request);

    ResultResponse getResult(String orderNumber);
    List<UserResultResponse> getResultByUserId(Long patientId);
}
