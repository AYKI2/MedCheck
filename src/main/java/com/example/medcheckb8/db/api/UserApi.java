package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class UserApi {
    private final UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping
    public List<UserResponse> getAllPatients(@RequestParam(required = false) String word) {
        return service.getAllPatients(word);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public SimpleResponse getProfile(@RequestParam Long id, @Valid @RequestBody ProfileRequest request) {
        return service.getProfile(id, request);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    public ProfileResponse getResult(Long id) {
        return service.getResult(id);
    }
}
