package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.AppointmentRequest;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;

public interface AppointmentService {
    AppointmentResponse save(AppointmentRequest request);
}
