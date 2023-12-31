package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.dto.response.UserGetResultResponse;
import com.example.medcheckb8.db.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllPatients(String word);

    SimpleResponse getProfile(ProfileRequest request);

    ProfileResponse getResult();

    SimpleResponse deleteById(Long id);
    UserGetResultResponse findById(Long id);

}
