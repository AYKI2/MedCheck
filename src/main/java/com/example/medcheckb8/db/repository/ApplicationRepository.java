package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}