package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.*;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.enums.Role;
import com.example.medcheckb8.db.exceptions.AlreadyExistException;
import com.example.medcheckb8.db.exceptions.BadCredentialException;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.dto.request.AuthenticationRequest;
import com.example.medcheckb8.db.dto.request.RegisterRequest;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.example.medcheckb8.db.repository.AccountRepository;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.AccountService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new AlreadyExistException("This email already exists!");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new AlreadyExistException("This number is already in use!");
        }
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .account(Account.builder().email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.PATIENT)
                        .build())
                .build();
        userRepository.save(user);

        String jwt = jwtService.generateToken(user.getAccount());
        return AuthenticationResponse.builder()
                .email(user.getAccount().getEmail())
                .token(jwt)
                .role(user.getAccount().getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!repository.existsByEmail(request.email())) {
            throw new BadRequestException("User with email: " + request.email() + " doesn't exists!");
        }
        Account account = repository.findByEmail(request.email()).orElseThrow(() -> new NotFountException(String.format("User with email: %s doesn't exists!", request.email())));
        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            throw new BadCredentialException("Invalid password!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String token = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .email(account.getEmail())
                .token(token)
                .role(account.getRole().name())
                .build();
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public SimpleResponse changePassword(ChangePasswordRequest request) {
        try {
            Account account = repository.findByEmail(getCurrentUser()).
                    orElseThrow(() -> new NotFountException(String.format("User with email : %s doesn't exists! ", getCurrentUser())));
            if (!passwordEncoder.matches(request.oldPassword(), account.getPassword())) {
                return SimpleResponse.builder().status(HttpStatus.NOT_FOUND).message("Wrong old password.").build();
            }
            account.setPassword(passwordEncoder.encode(request.newPassword()));
            repository.save(account);
            return SimpleResponse.builder().status(HttpStatus.OK).message("Password updated successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong.").build();
    }

    @Override
    public SimpleResponse forgotPassword(ForgotPasswordRequest request) {
        Account account = repository.findByEmail(request.email()).
                orElseThrow(() -> new NotFountException(String.format("User with email : %s doesn't exists! ", request.email())));
        String token = UUID.randomUUID().toString();
        account.setResetToken(token);
        repository.save(account);
        try {
            String senderName = "MedCheck user registration portal server";
            String subject = "Here's the link to reset your password";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("medcheck.service@gmail.com", senderName);
            helper.setTo(request.email());
            helper.setSubject(subject);
            String htmlMsg = "<p>Hello,</p>" +
                    "<p>You have requested to reset your password.</p>" +
                    "<p>Click the link below to change your password:</p>" +
                    "<p><b><a href=\"http://localhost:2023/processResetPassword=?" + token + "\">Change my password</a><b></p>" +
                    "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(message);
            return SimpleResponse.builder().status(HttpStatus.OK).message("Check your email for credentials.").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong.").build();
    }

    @Override
    public SimpleResponse resetToken(NewPasswordRequest newPasswordRequest) {
        Account account = repository.findByResetToken(newPasswordRequest.token()).
                orElseThrow(() -> new NotFountException("Invalid token"));

        account.setPassword(passwordEncoder.encode(newPasswordRequest.newPassword()));
        account.setResetToken(null);
        repository.save(account);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully updated!").build();
    }
}
