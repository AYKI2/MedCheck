package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.ResultResponse(r.user.id, concat(r.user.firstName,' ',r.user.lastName), r.user.phoneNumber, r.user.account.email, r.dateOfIssue,r.timeOfIssue,r.orderNumber, r.department.name, r.file) " +
            "from Result r where r.orderNumber=?1")
    Optional<ResultResponse> getResultByOrderNumber(String orderNumber);
}