package com.example.medcheckb8.api;

import com.example.medcheckb8.dto.request.AuthenticationRequest;
import com.example.medcheckb8.dto.request.RegisterRequest;
import com.example.medcheckb8.dto.response.AuthenticationResponse;
import com.example.medcheckb8.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountApi {
    private final AccountService service;

    public AccountApi(AccountService service) {
        this.service = service;
    }
    @PostMapping("/signUp")
    public AuthenticationResponse singUp(@RequestBody RegisterRequest request){
        return service.register(request);
    }
    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody AuthenticationRequest request){
        return service.authenticate(request);
    }
}
