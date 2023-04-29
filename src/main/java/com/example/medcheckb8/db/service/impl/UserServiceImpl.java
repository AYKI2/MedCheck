package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.AccountRepository;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AccountRepository accountRepository;
    private final JwtService service;

    @Override
    public List<UserResponse> getAllPatients(String word) {
        if (word == null) {
            return repository.getAllPatients();
        }
        return repository.getAllPatients(word);
    }

    @Override
    public SimpleResponse getProfile( ProfileRequest request) {
        User user = repository.findById(request.userId()).orElseThrow(() -> new NotFountException(String.format("User with email: %s not found!", request.userId())));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());
        user.getAccount().setEmail(request.email());
        repository.save(user);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully update!").build();
    }

    @Override
    public ProfileResponse getResult(String email) {
        Account account1 = service.getAccountInToken();
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new NotFountException(String.format("dswve")));
        ProfileResponse response = new ProfileResponse();
        response.setId(account.getId());
        response.setFirstName(account.getUser().getFirstName());
        response.setLastName(account.getUser().getLastName());
        response.setPhoneNumber(account.getUser().getPhoneNumber());
        response.setEmail(account.getEmail());
        return response;
    }
}
