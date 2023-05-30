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
@CrossOrigin
public class UserApi {
    private final UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "To receive all patients.",
            description = "With this method, the admin can get all the patients. Only for admin.")
    public List<UserResponse> getAllPatients(@RequestParam(required = false) String word) {
        return service.getAllPatients(word);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('PATIENT','ADMIN')")
    @Operation(
            summary = "The profile update method", description = "User profile which can see own information or changed"
    )
    public SimpleResponse update(@RequestBody @Valid ProfileRequest request) {
        return service.getProfile(request);
    }

    @GetMapping("/getResult")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @Operation(summary = "The method to send information ", description = "Information for autocomplete")
    public ProfileResponse getResult() {
        return service.getResult();
    }

    @DeleteMapping("/")
    @Operation(summary = "The patient delete method.",
            description = "This method should be used to delete the Patient")
    public SimpleResponse deleteById(@RequestParam Long id) {
        return service.deleteById(id);
    }
}
