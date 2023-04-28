package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.*;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

public interface AccountService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    SimpleResponse changePassword(ChangePasswordRequest request);

    SimpleResponse forgotPassword(ForgotPasswordRequest request);

    SimpleResponse resetToken(NewPasswordRequest newPasswordRequest);
}
