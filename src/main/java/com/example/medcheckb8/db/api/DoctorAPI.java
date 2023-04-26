package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
@Tag(name = "Doctor", description = "API endpoints for managing doctors profile!")
public class DoctorAPI {
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestParam Long departmentId,
                               @RequestBody @Valid DoctorRequest doctorRequest) {
        return doctorService.save(departmentId, doctorRequest);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DoctorResponse findById(@RequestParam Long doctorId) {
        return doctorService.findById(doctorId);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PATIENT')")
    public List<DoctorResponse> findAll() {
        return doctorService.getAll();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody @Valid DoctorRequest doctorRequest) {
        return doctorService.update(id, doctorRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@RequestParam Long doctorId) {
        return doctorService.delete(doctorId);
    }

    @PostMapping("/isActive")
    public SimpleResponse isActive(@RequestParam Boolean isActive,
                                   @RequestParam Long doctorId) {
        return doctorService.activateAndDeactivateDoctor(isActive, doctorId);
    }
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "The search method experts",description = "search for specialists in firstName,lastName,DepartmentName")
    List<DoctorResponse> search(@RequestParam String keyWord){
        return doctorService.searchByFirstNameOrLastNameOrDepartment(keyWord);
    }

}
