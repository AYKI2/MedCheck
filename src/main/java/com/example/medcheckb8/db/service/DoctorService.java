package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorExportResponse;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ExpertResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface DoctorService {
    SimpleResponse save(DoctorSaveRequest doctorRequest);

    DoctorResponse findById(Long id);

    List<ExpertResponse> getAllWithSearchExperts(String keyWord);

    SimpleResponse update(DoctorUpdateRequest doctorRequest);

    SimpleResponse delete(Long id);

    SimpleResponse activateAndDeactivateDoctor(Boolean isActive, Long doctorId);

    List<DoctorExportResponse> exportDoctorToExcel(HttpServletResponse response) throws IOException;
}
