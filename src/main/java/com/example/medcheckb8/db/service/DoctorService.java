package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    SimpleResponse save(DoctorSaveRequest doctorRequest);

    DoctorResponse findById(Long id);

    List<DoctorResponse> getAll();

    SimpleResponse update(DoctorUpdateRequest doctorRequest);

    SimpleResponse delete(Long id);

    SimpleResponse activateAndDeactivateDoctor(Boolean isActive, Long doctorId);
    Boolean booked(Long doctorId,LocalDateTime localDateTime);
    List<ScheduleResponse> freeSpecialists(String department, LocalDate localDate);
}
