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

import java.util.ArrayList;
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
        logger.info("Получение всех отделений");
        List<Department> all = departmentRepository.findAll();
        List<DepartmentResponse> departments = new ArrayList<>();
        for (Department department : all) {
            departments.add(DepartmentResponse.builder().name(department.getName().getTranslate()).build());
        }
        logger.info("Получено {} отделений" + departments.size());
        return departments;}

    @Override
    public DepartmentResponse findById(Long departmentId) {
        logger.info("Поиск отделения по ID: {}" + departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()->new NotFountException(String.format("Отделение с ID: %s не существует! ",departmentId)));
        return DepartmentResponse.builder().name(department.getName().getTranslate()).build();
    }
}
