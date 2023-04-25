package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.dto.response.UserResponse;

import java.beans.SimpleBeanInfo;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllPatients(String word);
    SimpleResponse getUserProfileById(Long id, ProfileRequest request);
}
