package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SearchResponse;
import com.example.medcheckb8.db.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.DoctorResponse(d.id, d.firstName, d.lastName, d.position," +
           " d.image, d.description, d.department.name) " +
           "from Doctor d")
    List<DoctorResponse> getAll();

    @Query("select new com.example.medcheckb8.db.dto.response.DoctorResponse(d.id, d.firstName, d.lastName, d.position," +
           " d.image, d.description, d.department.name) " +
           "from Doctor d where d.id=?1")
    Optional<DoctorResponse> findByDoctorId(Long id);

    @Query("select new com.example.medcheckb8.db.dto.response.SearchResponse(d.id, p.id, d.firstName, d.lastName, d.position, lower(p.name)) from Doctor d join d.department p" +
           " where  d.firstName ilike %:word% or d.lastName ilike %:word% or lower(p.name) ilike lower(concat('%', :word, '%'))")
    List<SearchResponse> globalSearch(String word);
}