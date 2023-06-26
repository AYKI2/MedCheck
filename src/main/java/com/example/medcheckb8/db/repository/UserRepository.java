package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByAccountId(Long id);

    @Query("select new com.example.medcheckb8.db.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.phoneNumber, u.account.email, r.dateOfIssue,r.timeOfIssue) from User u join Result r where r.user.id = u.id")
    Set<UserResponse> getAllPatients();

    @Query("select new com.example.medcheckb8.db.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.phoneNumber, u.account.email, r.dateOfIssue,r.timeOfIssue) " +
            "from User u  join Result r where r.user.id = u.id and" +
            "(u.firstName like %:word% or u.lastName like %:word%  or u.account.email like %:word%) ")
    Set<UserResponse> getAllPatients(String word);

}
