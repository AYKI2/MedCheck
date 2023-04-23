package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.ApplicationResponse(a.id,a.name,a.date,a.phoneNumber)from Application a")
    List<ApplicationResponse> getAllApplication();

    @Query("select new com.example.medcheckb8.db.dto.response.ApplicationResponse(a.id,a.name,a.date,a.phoneNumber) " +
            "from  Application a  where a.name  ilike  concat('%' ,:word, '%') ")
    List<ApplicationResponse> globalSearch(String word);

}