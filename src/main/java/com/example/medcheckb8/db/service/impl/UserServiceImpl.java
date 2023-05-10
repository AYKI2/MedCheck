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
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AccountRepository accountRepository;
    private final JwtService service;
    private static final Logger logger = Logger.getLogger(User.class.getName());

    @Override
    public List<UserResponse> getAllPatients(String word) {
        logger.info("Getting all patients with search word: {}" + word);
        if (word == null) {
            return repository.getAllPatients();
        }
        return repository.getAllPatients(word);
    }

    @Override
    public SimpleResponse getProfile(ProfileRequest request) {
        logger.info(String.format("Updating user profile for user with id %s", request.userId()));
        User user = repository.findById(request.userId()).orElseThrow(() -> new NotFountException(String.format("User with email: %s not found!", request.userId())));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());
        user.getAccount().setEmail(request.email());
        repository.save(user);
        logger.info(String.format("User profile updated for user with id %s", request.userId()));
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully update!").build();
    }

    @Override
    public ProfileResponse getResult(String email) {
        logger.info("Getting profile for email: " + email);
        Account account1 = service.getAccountInToken();
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new NotFountException(String.format("dswve")));
        ProfileResponse response = new ProfileResponse();
        response.setId(account.getId());
        response.setFirstName(account.getUser().getFirstName());
        response.setLastName(account.getUser().getLastName());
        response.setPhoneNumber(account.getUser().getPhoneNumber());
        response.setEmail(account.getEmail());
        logger.info("Returning profile for email: " + email);
        return response;
    }
}
