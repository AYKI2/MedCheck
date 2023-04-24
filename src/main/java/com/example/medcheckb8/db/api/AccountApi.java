package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.AuthenticationRequest;
import com.example.medcheckb8.db.dto.request.ChangePasswordRequest;
import com.example.medcheckb8.db.dto.request.ForgotPasswordRequest;
import com.example.medcheckb8.db.dto.request.RegisterRequest;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AccountApi {
    private final AccountService service;

    @PostMapping("/signUp")
    public AuthenticationResponse singUp(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody @Valid AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @PostMapping("/changePassword")
    SimpleResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return service.changePassword(request);
    }

    @PostMapping("/forgot_password")
    SimpleResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return service.forgotPassword(request);
    }
}
