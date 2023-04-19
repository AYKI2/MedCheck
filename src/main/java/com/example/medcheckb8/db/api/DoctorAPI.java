package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorAPI {
    private final DoctorService doctorService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestParam Long departmentId,
                               @RequestBody @Valid DoctorRequest doctorRequest) {
        return doctorService.save(departmentId, doctorRequest);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DoctorResponse findById(@PathVariable Long id) {
        return doctorService.findById(id);
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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id) {
        return doctorService.delete(id);
    }

    @PostMapping("/isActive")
    public SimpleResponse isActive(@RequestParam(required = false) Boolean isActive,
                                   @RequestParam(required = false) Long doctorId) {
        return doctorService.isActive(isActive, doctorId);
    }

}
