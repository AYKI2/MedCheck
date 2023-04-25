package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("select new com.example.medcheckb8.db.dto.response.ProfileResponse(u.id,u.firstName,u.lastName,u.phoneNumber,u.account.email)from User u")
    SimpleResponse getUserProfileById(Long id);
    @Query("select new com.example.medcheckb8.db.dto.response.ProfileResponse(u.id,u.firstName,u.lastName,u.phoneNumber,u.account.email)from User u where u.id=:id")
    ProfileResponse getProfile(Long id);

}
