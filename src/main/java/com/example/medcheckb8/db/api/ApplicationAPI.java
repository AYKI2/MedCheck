package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
@CrossOrigin
public class ApplicationAPI {
    private final ApplicationService service;

    @PostMapping("/add")
    @Operation(summary = "Method for saving the request.",
            description = "You can apply using this method.")
    public SimpleResponse addApplication(@RequestBody @Valid ApplicationRequest request) {
        return service.addApplication(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getAll")
    @Operation(summary = "Way to receive all applications.",
            description = "With this method, the admin can get all the abandoned applications. Only for admin.")
    public List<ApplicationResponse> getAllApplication(@RequestParam(required = false) String word) {
        return service.getAllApplication(word);
    }
}
