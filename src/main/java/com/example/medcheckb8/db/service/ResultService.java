package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;

public interface ResultService {
    ResultResponse addResult(ResultRequest request);
}
