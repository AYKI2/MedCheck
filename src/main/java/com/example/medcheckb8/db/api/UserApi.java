package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class UserApi {
    private final UserService service;

    @PutMapping
    public SimpleResponse getUserProfileById(@RequestParam Long id,@Valid @RequestBody ProfileRequest request) {
        return service.getUserProfileById(id, request);

    }
    @GetMapping("/get")
    public ProfileResponse getProfile(@RequestParam(required = false) Long id){
        return service.getProfile(id);

    }
}
