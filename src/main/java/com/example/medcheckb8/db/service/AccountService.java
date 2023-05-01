package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.*;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.google.firebase.auth.FirebaseAuthException;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

public interface AccountService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;


    SimpleResponse changePassword(ChangePasswordRequest request);

    SimpleResponse forgotPassword(ForgotPasswordRequest request);

    SimpleResponse resetToken(NewPasswordRequest newPasswordRequest);
}
