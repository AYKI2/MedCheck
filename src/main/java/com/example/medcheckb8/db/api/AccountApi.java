package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.*;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.AccountService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Метод для регистрации новых пользователей.",
            description = "С помощью этого метода вы можете зарегистрировать нового пользователя. Метод доступен всем.")
    public AuthenticationResponse singUp(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/signIn")
    @Operation(summary = "Метод авторизации для существующих пользователей.",
            description = "С помощью этого метода вы можете авторизовать существующего пользователя для работы с другими методами. " +
                    "Метод доступен всем.")
    public AuthenticationResponse signIn(@RequestBody @Valid AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @PostMapping("/auth-google")
    @Operation(summary = "Метод авторизации для существующих пользователей и регистрации для несуществующих пользователей через Google.",
            description = "С помощью этого метода вы можете войти или зарегистрироваться через Google. Метод доступен для всех.")
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return service.authWithGoogle(tokenId);
    }

    @PostMapping("/changePassword")
    @Operation(summary = "Метод для изменения пароля пользователя.",
            description = "Способ изменения пароля текущего пользователя через его профиль. Только для пациента.")
    SimpleResponse changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return service.changePassword(request);
    }

    @PostMapping("/forgot_password")
    @Operation(summary = "Метод восстановления пароля.",
            description = "Способ изменения пароля пользователя, если он был утерян. Метод доступен только пользователю. Только для пациента.")
    SimpleResponse forgotPassword(@RequestParam String email, @RequestParam String link) {
        return service.forgotPassword(email, link);
    }

    @PostMapping("/reset_password")
    @Operation(summary = "Метод сброса пароля.",
            description = "Метод для сброса пароля пользователя. Метод доступен только пользователю. Только для пациента.")
    public SimpleResponse resetPassword(@RequestBody @Valid NewPasswordRequest newPassword) {
        return service.resetToken(newPassword);

    }
}

