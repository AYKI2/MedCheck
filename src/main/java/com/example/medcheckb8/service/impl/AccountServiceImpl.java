package com.example.medcheckb8.service.impl;

import com.example.medcheckb8.config.JwtService;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.enums.Role;
import com.example.medcheckb8.dto.request.AuthenticationRequest;
import com.example.medcheckb8.dto.request.RegisterRequest;
import com.example.medcheckb8.dto.response.AuthenticationResponse;
import com.example.medcheckb8.repository.AccountRepository;
import com.example.medcheckb8.repository.UserRepository;
import com.example.medcheckb8.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .build();
        Account account = Account.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.PATIENT)
                .user(user)
                .build();
        user.setAccount(account);
        userRepository.save(user);
        repository.save(account);

        String jwt = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .email(account.getEmail())
                .token(jwt)
                .role(account.getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        Account account = repository.findByEmail(request.email())
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("User with email: %s doesn't exists!", request.email())));

        String token = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .email(account.getEmail())
                .token(token)
                .role(account.getRole().name())
                .build();
    }
}
