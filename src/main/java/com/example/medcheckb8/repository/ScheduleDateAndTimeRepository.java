package com.example.medcheckb8.repository;

import com.example.medcheckb8.entities.ScheduleDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDateAndTimeRepository extends JpaRepository<ScheduleDateAndTime, Long> {
}