package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByAccountId(Long id);

    @Query("SELECT DISTINCT new com.example.medcheckb8.db.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.phoneNumber, u.account.email, r.dateOfIssue, r.timeOfIssue) " +
            "FROM User u " +
            "JOIN Result r ON r.user.id = u.id " +
            "WHERE u.firstName <> 'Админ'")
    List<UserResponse> getAllPatients();

    @Query("SELECT DISTINCT new com.example.medcheckb8.db.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.phoneNumber, u.account.email, r.dateOfIssue, r.timeOfIssue) " +
            "FROM User u " +
            "JOIN Result r ON r.user.id = u.id " +
            "WHERE u.firstName <> 'Админ' AND " +
            "(u.firstName LIKE %:word% OR u.lastName LIKE %:word% OR u.account.email LIKE %:word%)")
    List<UserResponse> getAllPatients(@Param("word") String word);

}
