package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentApi {

    private final AppointmentService appointmentService;

    @GetMapping("/get")
    public List<AppointmentResponse> myAppointments(@RequestParam Long userId){
        return appointmentService.getAppointmentsByUserId(userId);
    }
}
