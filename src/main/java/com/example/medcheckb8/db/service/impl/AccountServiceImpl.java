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
import com.example.medcheckb8.db.service.EmailSenderService;
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
    private final EmailSenderService emailService;
    private static final Logger logger = Logger.getLogger(Account.class.getName());

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        logger.info("Регистрация пользователя: " + request.email());
        if (repository.existsByEmail(request.email())) {
            logger.warning("Email уже существует: " + request.email());
            throw new AlreadyExistException("Этот email уже существует!");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            logger.warning("Номер телефона уже используется:  " + request.phoneNumber());
            throw new AlreadyExistException("Этот номер уже используется!");
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
        logger.info("Пользователь успешно зарегистрирован: " + user.getAccount().getEmail());

        String jwt = jwtService.generateToken(user.getAccount());
        return AuthenticationResponse.builder()
                .email(user.getAccount().getEmail())
                .token(jwt)
                .role(user.getAccount().getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Аутентификация пользователя:  " + request.email());
        if (!repository.existsByEmail(request.email())) {
            logger.warning("Пользователь не найден: " + request.email());
            throw new BadRequestException("Пользователь с email: " + request.email() + "не существует");
        }
        Account account = repository.findByEmail(request.email()).orElseThrow(() -> new NotFountException(String.format("Пользователь с email: %s не существует!", request.email())));
        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            logger.warning("Неверный пароль для пользователя " + request.email());
            throw new BadCredentialException("Неверный пароль!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        logger.info("Пользователь успешно аутентифицирован: " + request.email());
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
            log.info("Метод init успешно выполнен");
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
            logger.info("Приложение Firebase успешно инициализировано.");
        } catch (IOException e) {
            logger.severe("Произошла ошибка ввода-вывода при инициализации приложения " + e.getMessage());
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
            log.error(String.format("Пользователь с таким email-адресом %s не найден!", firebaseToken.getEmail()));
            return new NotFountException(String.format("Пользователь с таким email-адресом %s не найден!", firebaseToken.getEmail()));
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
        logger.info("Получение текущего пользователя: " + username);
        return username;
    }

    @Override
    public SimpleResponse changePassword(ChangePasswordRequest request) {
        try {
            Account account = repository.findByEmail(getCurrentUser()).
                    orElseThrow(() -> new NotFountException(String.format("Пользователь с email : %s не существует! ", getCurrentUser())));
            if (!passwordEncoder.matches(request.oldPassword(), account.getPassword())) {
                logger.log(Level.WARNING, "Неверный старый пароль для пользователя  " + getCurrentUser());
                return SimpleResponse.builder().status(HttpStatus.NOT_FOUND).message("Неверный старый пароль").build();
            }
            account.setPassword(passwordEncoder.encode(request.newPassword()));
            repository.save(account);
            logger.log(Level.INFO, "Пароль успешно обновлен для пользователя " + getCurrentUser());
            return SimpleResponse.builder().status(HttpStatus.OK).message("Пароль успешно обновлен.").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Произошла ошибка при изменении пароля для пользователя " + getCurrentUser(), e);
        }
        return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Произошла ошибка.").build();
    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) {
        Account account = repository.findByEmail(email).
                orElseThrow(() -> new NotFountException(String.format("Пользователь с email : %s не существует! ", email)));
        String token = UUID.randomUUID().toString();
        account.setResetToken(token);
        repository.save(account);
        try {
            String resetPasswordLink = link + "/" + token;
            String subject = "Вот ссылка для сброса пароля";
            Context context = new Context();
            context.setVariable("title", "Сброс пароля");
            context.setVariable("message", "Здравствуйте" + account.getUsername() + "" +
                    " Чтобы сбросить пароль, перейдите по ссылке ниже:");
            context.setVariable("link", resetPasswordLink);
            context.setVariable("tokenTitle", "Сброс пароля");
            String htmlContent = templateEngine.process("reset-password-template.html", context);
            emailService.sendEmail(email, subject, htmlContent);
            log.info("Отправлено письмо для сброса пароля на адрес: {}", email);
        } catch (NotFountException ex) {
            log.error("Пользователь не найден при обновлении токена сброса пароля для email: {}", email);
            return SimpleResponse.builder().
                    status(HttpStatus.OK).
                    message("Проверьте электронную почту для получения инструкций по сбросу пароля.")
                    .build();
        }
        log.info("Обновлен токен сброса пароля для email: {}", email);
        return SimpleResponse.builder().
                status(HttpStatus.OK).
                message("Проверьте электронную почту для получения инструкций по сбросу пароля.")
                .build();
    }

    @Override
    public SimpleResponse resetToken(NewPasswordRequest newPasswordRequest) {
        logger.info("Сброс пароля для токена: {}" + newPasswordRequest.token());
        try {
            Account account = repository.findByResetToken(newPasswordRequest.token()).
                    orElseThrow(() -> new NotFountException("Неверный токен"));

            account.setPassword(passwordEncoder.encode(newPasswordRequest.newPassword()));
            account.setResetToken(null);
            repository.save(account);
            logger.info("Сброс пароля успешно выполнен для токена: {}" + newPasswordRequest.token());
            return SimpleResponse.builder().status(HttpStatus.OK).message("Успешно обновлено!").build();
        } catch (NotFountException e) {
            logger.severe("Ошибка сброса пароля для токена: {}" + newPasswordRequest.token());
            return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Произошла ошибка.").build();
        }
    }
}
