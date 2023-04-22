package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("select new com.example.medcheckb8.db.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.phoneNumber, u.account.email, r.dateOfIssue) from User u join Result r where r.user.id = u.id")
    List<UserResponse>getAllPatients();
    @Query("select new com.example.medcheckb8.db.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.phoneNumber, u.account.email, r.dateOfIssue) from User u  join Result r where r.user.id = u.id and(u.firstName like %:word% or u.lastName like %:word%  or u.account.email like %:word%) ")
    List<UserResponse>getAllPatients(String word);

}
