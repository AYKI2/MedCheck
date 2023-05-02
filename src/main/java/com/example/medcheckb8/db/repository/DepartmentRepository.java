package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.DepartmentResponse;
import com.example.medcheckb8.db.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.DepartmentResponse(d.name) from Department d ")
    List<DepartmentResponse> getAllDepartments();
    @Query("select new com.example.medcheckb8.db.dto.response.DepartmentResponse(d.name) from Department d where d.id=?1")
    Optional<DepartmentResponse> findByIdDepartment(Long departmentId);
}