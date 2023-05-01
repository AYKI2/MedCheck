package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
                .account(Account.builder()
                        .email(request.email())
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
        Account account = repository.findByEmail(request.email())
                .orElseThrow(() ->
                        new NotFountException(String.format("User with email: %s doesn't exists!", request.email())));
        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            throw new BadCredentialException("Invalid password!");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
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
        } catch (IOException e) {
            log.error("IOException");
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
}
