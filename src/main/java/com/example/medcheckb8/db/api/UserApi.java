package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
