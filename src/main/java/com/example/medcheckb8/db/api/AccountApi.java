package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.*;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/changePassword")
    SimpleResponse changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return service.changePassword(request);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/forgot_password")
    SimpleResponse forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return service.forgotPassword(request);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/reset_password")
    public SimpleResponse resetPassword(@RequestBody @Valid NewPasswordRequest newPassword) {
        return service.resetToken(newPassword);

    }
}

