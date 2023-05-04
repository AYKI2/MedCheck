package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.AppointmentRequest;
import com.example.medcheckb8.db.dto.request.FreeSpecialistRequest;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.dto.response.GetAllAppointmentResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.AppointmentService;
import com.example.medcheckb8.db.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Method for adding a new entry.",
            description = "Using this method, the patient can make an appointment with a doctor.Only for patients.")
    public AppointmentResponse addAppointment(@RequestBody @Valid AppointmentRequest request) {
        return service.save(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Get all appointments.",
            description = "Using this method, the admin can see all appointments.Only for admin.")
    public List<GetAllAppointmentResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "To get the closest free time.",
            description = "Using this method, the patient can get the nearest free time.Only for patients.")
    public List<ScheduleResponse> getTheNearestFreeDoctors(@RequestBody @Valid FreeSpecialistRequest request) {
        return doctorService.findDoctorsByDate(request.department(), request.zonedDateTime());
    }

    @PostMapping("/canceled")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "To canceled the appointment.",
            description = "Using this method, the patient can cancel the doctor's appointment.Only for patients.")
    public SimpleResponse canceled(@RequestParam Long appointmentId) {
        return service.canceled(appointmentId);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @Operation(summary = "To delete appointments.",
            description = "This method can be used by both the admin and the patient. When using the method as a patient," +
                    "you don't have to give an 'id', since the patient can only delete all appointments, while the admin can" +
                    " delete them one by one or all.")
    public SimpleResponse delete(@RequestParam(required = false) List<Long> appointments) {
        return service.delete(appointments);
    }
}
