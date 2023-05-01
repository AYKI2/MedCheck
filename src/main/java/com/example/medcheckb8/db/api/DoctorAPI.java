package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
@Tag(name = "doctor", description = "Expert API Endpoints")
public class DoctorAPI {
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody @Valid DoctorSaveRequest doctorRequest) {
        return doctorService.save(doctorRequest);
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

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse update(@RequestBody @Valid DoctorUpdateRequest doctorRequest) {
        return doctorService.update(doctorRequest);
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

    @GetMapping("/export-to-excel")
    @Operation(
            summary = "The Export to Excel method",
            description = "Using the method, export dataBase doctors values to Excel values.")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Doctors_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        doctorService.exportDoctorToExcel(response);
    }

}
