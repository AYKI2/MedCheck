package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.repository.AppointmentRepository;
import com.example.medcheckb8.db.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class  AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final JwtService jwtService;

    @Override
    public List<AppointmentResponse> getUserAppointments() {
        Account account = jwtService.getAccountInToken();
        return appointmentRepository.getUserAppointments(account.getEmail());
    }
}
