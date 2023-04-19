package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.DoctorRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

import java.util.List;

public interface DoctorService {
    SimpleResponse save(Long departmentId, DoctorRequest doctorRequest);

    DoctorResponse findById(Long id);

    List<DoctorResponse> getAll();

    SimpleResponse update(Long id, DoctorRequest doctorRequest);

    SimpleResponse delete(Long id);

    SimpleResponse isActive(Boolean isActive, Long doctorId);
}
