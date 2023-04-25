package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.User;

public interface UserService {
    SimpleResponse getUserProfileById(Long id, ProfileRequest request);
    ProfileResponse getProfile(Long id);
}
