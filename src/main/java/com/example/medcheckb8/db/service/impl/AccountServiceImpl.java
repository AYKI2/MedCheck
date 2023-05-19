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
import com.example.medcheckb8.db.service.EmailService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;
    private static final Logger logger = Logger.getLogger(Account.class.getName());

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        logger.info("Registering user: " + request.email());
        if (repository.existsByEmail(request.email())) {
            logger.warning("Email already exists: " + request.email());
            throw new AlreadyExistException("This email already exists!");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            logger.warning("Phone number already in use: " + request.phoneNumber());
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
        logger.info("User registered successfully: " + user.getAccount().getEmail());

        String jwt = jwtService.generateToken(user.getAccount());
        return AuthenticationResponse.builder()
                .email(user.getAccount().getEmail())
                .token(jwt)
                .role(user.getAccount().getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Authenticating user: " + request.email());
        if (!repository.existsByEmail(request.email())) {
            logger.warning("User not found: " + request.email());
            throw new BadRequestException("User with email: " + request.email() + " doesn't exists!");
        }
        Account account = repository.findByEmail(request.email()).orElseThrow(() -> new NotFountException(String.format("User with email: %s doesn't exists!", request.email())));
        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            logger.warning("Invalid password for user: " + request.email());
            throw new BadCredentialException("Invalid password!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        logger.info("User authenticated successfully: " + request.email());
        String token = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .email(account.getEmail())
                .token(token)
                .role(account.getRole().name())
                .build();
    }

    @PostConstruct
    public void init() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.
                    fromStream(new ClassPathResource("medcheck-firebase.json").getInputStream());
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            log.info("Successfully worked the init method");
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
            logger.info("Firebase app initialized successfully.");
        } catch (IOException e) {
            logger.severe("IOException occurred while initializing Firebase app: " + e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        if (!repository.existsByEmail(firebaseToken.getEmail())) {
            User newUser = new User();
            String[] name = firebaseToken.getName().split(" ");
            newUser.setFirstName(name[0]);
            newUser.setLastName(name[1]);
            newUser.getAccount().setEmail(firebaseToken.getEmail());
            newUser.getAccount().setPassword(firebaseToken.getEmail());
            newUser.getAccount().setRole(Role.PATIENT);
            userRepository.save(newUser);
        }
        Account account = repository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> {
            log.error(String.format("User with this email address %s was not found!", firebaseToken.getEmail()));
            return new NotFountException(String.format("User with this email address %s was not found!", firebaseToken.getEmail()));
        });

        String token = jwtService.generateToken(account);
        log.info("Successfully worked the authorization with Google");

        return AuthenticationResponse.builder()
                .email(firebaseToken.getEmail())
                .token(token)
                .role(account.getRole().name())
                .build();
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        logger.info("Getting current user: " + username);
        return username;
    }

    @Override
    public SimpleResponse changePassword(ChangePasswordRequest request) {
        try {
            Account account = repository.findByEmail(getCurrentUser()).
                    orElseThrow(() -> new NotFountException(String.format("User with email : %s doesn't exists! ", getCurrentUser())));
            if (!passwordEncoder.matches(request.oldPassword(), account.getPassword())) {
                logger.log(Level.WARNING, "Wrong old password for user " + getCurrentUser());
                return SimpleResponse.builder().status(HttpStatus.NOT_FOUND).message("Wrong old password.").build();
            }
            account.setPassword(passwordEncoder.encode(request.newPassword()));
            repository.save(account);
            logger.log(Level.INFO, "Password updated successfully for user " + getCurrentUser());
            return SimpleResponse.builder().status(HttpStatus.OK).message("Password updated successfully.").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while changing password for user " + getCurrentUser(), e);
        }
        return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong.").build();
    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) {
        Account account = repository.findByEmail(email).
                orElseThrow(() -> new NotFountException(String.format("User with email : %s doesn't exists! ", email)));
        String token = UUID.randomUUID().toString();
        account.setResetToken(token);
        repository.save(account);
        try {
            String resetPasswordLink = link + "/" + token;
            String subject = "Here's the link to reset your password";
            Context context = new Context();
            context.setVariable("title", "Password Reset");
            context.setVariable("message", "Hello " + account.getUsername() + "" +
                    " Click the link below to reset your password:");
            context.setVariable("link", resetPasswordLink);
            context.setVariable("tokenTitle", "Reset Password");
            String htmlContent = templateEngine.process("reset-password-template.html", context);
            emailService.sendEmail(email, subject, htmlContent);
            log.info("Password reset email sent to: {}", email);
        } catch (NotFountException ex) {
            log.error("User not found while updating reset password token for email: {}", email);
            return SimpleResponse.builder().
                    status(HttpStatus.OK).
                    message("Please check your email inbox for password reset instructions.")
                    .build();
        }
        log.info("Reset password token updated for email: {}", email);
        return SimpleResponse.builder().
                status(HttpStatus.OK).
                message("Please check your email inbox for password reset instructions.")
                .build();
    }

    @Override
    public SimpleResponse resetToken(NewPasswordRequest newPasswordRequest) {
        logger.info("Resetting password for token: {}" + newPasswordRequest.token());
        try {
            Account account = repository.findByResetToken(newPasswordRequest.token()).
                    orElseThrow(() -> new NotFountException("Invalid token"));

            account.setPassword(passwordEncoder.encode(newPasswordRequest.newPassword()));
            account.setResetToken(null);
            repository.save(account);
            logger.info("Password reset successful for token: {}" + newPasswordRequest.token());
            return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully updated!").build();
        } catch (NotFountException e) {
            logger.severe("Error resetting password for token: {}" + newPasswordRequest.token());
            return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong.").build();
        }
    }
}
