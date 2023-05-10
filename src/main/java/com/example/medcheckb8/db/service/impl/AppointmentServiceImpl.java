package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.entities.Appointment;
import com.example.medcheckb8.db.repository.AppointmentRepository;
import com.example.medcheckb8.db.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class  AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final JwtService jwtService;
    private static final Logger logger = Logger.getLogger(Appointment.class.getName());

    @Override
    public List<AppointmentResponse> getUserAppointments() {
        Account account = jwtService.getAccountInToken();
        logger.info("Retrieving appointments for user with email: {}" + account.getEmail());
        List<AppointmentResponse> appointments = appointmentRepository.getUserAppointments(account.getEmail());
        logger.severe("Retrieved {} appointments for user with email: {}" + appointments.size() + account.getEmail());
        return appointments;
    }
}
