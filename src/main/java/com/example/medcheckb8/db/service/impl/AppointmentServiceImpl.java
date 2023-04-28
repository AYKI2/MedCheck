package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.repository.AppointmentRepository;
import com.example.medcheckb8.db.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentResponse> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.getAppointmentsByUserId(userId);
    }
}
