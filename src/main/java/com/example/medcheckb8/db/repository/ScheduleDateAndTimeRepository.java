package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDateAndTimeRepository extends JpaRepository<ScheduleDateAndTime, Long> {
}