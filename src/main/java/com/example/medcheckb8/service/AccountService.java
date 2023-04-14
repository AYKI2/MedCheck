package com.example.medcheckb8.service;

import com.example.medcheckb8.dto.request.AuthenticationRequest;
import com.example.medcheckb8.dto.request.RegisterRequest;
import com.example.medcheckb8.dto.response.AuthenticationResponse;

public interface AccountService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
