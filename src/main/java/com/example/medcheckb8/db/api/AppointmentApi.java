package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.AppointmentRequest;
import com.example.medcheckb8.db.dto.request.FreeSpecialistRequest;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.dto.response.GetAllAppointmentResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.AppointmentService;
import com.example.medcheckb8.db.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentApi {
    private final AppointmentService service;
    private final DoctorService doctorService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public AppointmentResponse addAppointment(@RequestBody @Valid AppointmentRequest request) {
        return service.save(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<GetAllAppointmentResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    public List<ScheduleResponse> getTheNearestFreeDoctors(@RequestBody @Valid FreeSpecialistRequest request) {
        return doctorService.findDoctorsByDate(request.department(), request.zonedDateTime());
    }

    @PostMapping("/canceled")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public SimpleResponse canceled(@RequestParam Long appointmentId) {
        return service.canceled(appointmentId);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    public SimpleResponse delete(@RequestParam(required = false) List<Long> appointments) {
        return service.delete(appointments);
    }
}
