package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.AppointmentRequest;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentApi {
    private final AppointmentService service;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    public AppointmentResponse addAppointment(@RequestBody @Valid AppointmentRequest request) {
        return service.save(request);
    }
}
