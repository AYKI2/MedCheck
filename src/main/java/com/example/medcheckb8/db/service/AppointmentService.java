package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> getUserAppointments();
}
