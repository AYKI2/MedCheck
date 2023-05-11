package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.DepartmentResponse;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    @Override
    public List<DepartmentResponse> getAllDepartment() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public DepartmentResponse findById(Long departmentId) {
        return departmentRepository.findByIdDepartment(departmentId)
                .orElseThrow(()->new NotFountException(String.format("This is department : %s doesn't exists! ",departmentId)));
    }
}
