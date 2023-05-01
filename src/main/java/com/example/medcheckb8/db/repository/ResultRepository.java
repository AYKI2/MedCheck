package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}