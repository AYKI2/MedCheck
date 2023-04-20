package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.repository.ResultRepository;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    @Override
    public ResultResponse addResult(ResultRequest request) {
        return null;
    }
}
