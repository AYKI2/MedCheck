package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.DepartmentResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private static final Logger logger = Logger.getLogger(Department.class.getName());

    @Override
    public List<DepartmentResponse> getAllDepartment() {
        logger.info("Getting all departments");
        List<DepartmentResponse> departments = departmentRepository.getAllDepartments();
        logger.info("Retrieved {} departments" + departments.size());
        return departments;    }

    @Override
    public DepartmentResponse findById(Long departmentId) {
        logger.info("Finding department by ID: {}" + departmentId);
        return departmentRepository.findByIdDepartment(departmentId)
                .orElseThrow(()->new NotFountException(String.format("This is department : %s doesn't exists! ",departmentId)));
    }
}
