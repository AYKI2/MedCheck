package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.enums.Detachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(Detachment name);
}