package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

public interface ResultService {
    SimpleResponse addResult(ResultRequest request);

    ResultResponse getResult(String orderNumber);
}
