package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.AuthenticationRequest;
import com.example.medcheckb8.db.dto.request.ChangePasswordRequest;
import com.example.medcheckb8.db.dto.request.ForgotPasswordRequest;
import com.example.medcheckb8.db.dto.request.RegisterRequest;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

import java.util.Map;

public interface AccountService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    SimpleResponse changePassword(ChangePasswordRequest request);

    SimpleResponse forgotPassword(ForgotPasswordRequest request);
}
