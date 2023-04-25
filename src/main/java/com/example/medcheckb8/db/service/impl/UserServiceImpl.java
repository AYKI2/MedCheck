package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserResponse> getAllPatients(String word) {
        if (word == null) {
            return repository.getAllPatients();
        }
        return repository.getAllPatients(word);
    }
}
