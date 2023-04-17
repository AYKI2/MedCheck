package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.AuthenticationRequest;
import com.example.medcheckb8.db.dto.request.RegisterRequest;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.service.AccountService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AccountApi {
    private final AccountService service;
    public AccountApi(AccountService service) {
        this.service = service;
    }
    @PostMapping("/signUp")
    public AuthenticationResponse singUp(@RequestBody @Validated RegisterRequest request){
        return service.register(request);
    }
    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody @Validated AuthenticationRequest request){
        return service.authenticate(request);
    }
}
