package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.*;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.AccountService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AccountApi {
    private final AccountService service;

    @PostMapping("/signUp")
    @Operation(summary = "Method for registering new users.",
            description = "Using this method, you can register a new user, the method is available to everyone.")
    public AuthenticationResponse singUp(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/signIn")
    @Operation(summary = "Authorization method for existing users.",
            description = "With this method, you can authorize an existing user to work with other methods, " +
                    "the method is available to everyone.")
    public AuthenticationResponse signIn(@RequestBody @Valid AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @PostMapping("/auth-google")
    @Operation(summary = "Authorization method for existing and registration for non-existent users via google.",
            description = "Using this method, you can log in or register through Google, the method is available to everyone.")
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return service.authWithGoogle(tokenId);
    }

    @PostMapping("/changePassword")
    @Operation(summary = "Method for changing the user's password.",
            description = "A way to change the password of the current user through his profile.Only for patient.")
    SimpleResponse changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return service.changePassword(request);
    }

    @PostMapping("/forgot_password")
    @Operation(summary = "Method for password recovery.",
            description = "A way to change a user's password if it is lost. The method is only available to the user.Only for Patient.")
    SimpleResponse forgotPassword(@RequestParam String email,@RequestParam String link) {
        return service.forgotPassword(email,link);
    }

    @PostMapping("/reset_password")
    @Operation(summary = "Password reset method.",
            description = "Method for resetting a user's password. The method is only available to the user.Only for Patient.")
    public SimpleResponse resetPassword(@RequestBody @Valid NewPasswordRequest newPassword) {
        return service.resetToken(newPassword);

    }
}

