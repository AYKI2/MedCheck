package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorScheduleRequest;
import com.example.medcheckb8.db.dto.request.appointment.InstallByTemplate;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@Tag(name = "Schedule API ")
@CrossOrigin
public class ScheduleApi {
    private final ScheduleService service;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Schedule search and get all schedule",
            description = " Using this method you can find schedules by export names or see all experts with their schedules")
    public List<ScheduleResponse> getAll(@RequestParam(required = false) String word,
                                         @RequestParam(required = false) LocalDate startDate,
                                         @RequestParam(required = false) LocalDate endDate) {
        return service.getAllSchedule(word, startDate, endDate);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Add doctor's schedule",
            description = "With this method, you can add a doctor's schedule. Available for admin only.")
    public SimpleResponse save(@RequestBody DoctorScheduleRequest request) {
        return service.save(request);
    }

    @PostMapping("/template")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Install by template",
            description = "With this method, you can set one doctor's schedule for other doctors.Only for admin.")
    public SimpleResponse installByTemplate(@RequestBody InstallByTemplate request){
        return service.installByTemplate(request);
    }
}
