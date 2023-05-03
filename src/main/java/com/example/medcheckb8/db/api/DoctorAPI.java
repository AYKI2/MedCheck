package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ExpertResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorAPI {
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert save method.",
            description = "This method should be used to save the Expert")
    public SimpleResponse save(@RequestBody @Valid DoctorSaveRequest doctorRequest) {
        return doctorService.save(doctorRequest);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert find method.",
            description = "This method should be used to find the Expert by id")
    public DoctorResponse findById(@RequestParam Long doctorId) {
        return doctorService.findById(doctorId);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "The  search method experts", description = "Using the method, you can find" +
            " a doctor by name and surname," +
            " you can find a department")
    List<ExpertResponse> search(@RequestParam(required = false) String keyWord) {
        return doctorService.getAllWithSearchExperts(keyWord);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert update method.",
            description = "This method should be used to update the Expert")
    public SimpleResponse update(@RequestBody @Valid DoctorUpdateRequest doctorRequest) {
        return doctorService.update(doctorRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert delete method.",
            description = "This method should be used to delete the Expert")
    public SimpleResponse delete(@RequestParam Long doctorId) {
        return doctorService.delete(doctorId);
    }

    @PostMapping("/isActive")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert activate and deactivate method.",
            description = "This method should be used to activate and deactivate the Expert.")
    public SimpleResponse isActive(@RequestParam Boolean isActive,
                                   @RequestParam Long doctorId) {
        return doctorService.activateAndDeactivateDoctor(isActive, doctorId);
    }

}
