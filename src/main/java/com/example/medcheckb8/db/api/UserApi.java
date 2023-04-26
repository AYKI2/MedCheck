package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
@Tag(name = "User", description = "API endpoints for managing users profile3")
public class UserApi {
    private final UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping
    public List<UserResponse> getAllPatients(@RequestParam(required = false) String word) {
        return service.getAllPatients(word);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(
            summary = "The profile update method", description = "User profile which can see own information or changed"
    )
    public SimpleResponse getProfile(@RequestParam Long id, @RequestBody @Valid ProfileRequest request) {
        return service.getProfile(id, request);
    }

    @GetMapping("/getResult")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @Operation(summary = "The method to send information ", description = "Information for autocomplete")
    public ProfileResponse getResult(Long id) {
        return service.getResult(id);
    }
}
