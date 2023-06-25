package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.*;
import com.example.medcheckb8.db.dto.response.appointment.ScheduleResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface DoctorService {
    SimpleResponse save(DoctorSaveRequest doctorRequest);

    DoctorResponse findById(Long id);

    PaginationExperts getAllWithSearchExperts(String keyWord , int page , int size);

    SimpleResponse update(DoctorUpdateRequest doctorRequest);

    SimpleResponse delete(Long id);

    SimpleResponse activateAndDeactivateDoctor(Boolean isActive, Long doctorId);

    List<ScheduleResponse> findDoctorsByDate(String department, String timeZone);
    
    void exportDoctorToExcel(HttpServletResponse response) throws IOException;
    List<OurDoctorsResponse> findByDepartmentName(String name);
}
