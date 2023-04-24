package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllPatients(String word);
}
