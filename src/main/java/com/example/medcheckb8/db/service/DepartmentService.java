package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAllDepartment();

    DepartmentResponse findById(Long departmentId);
}
