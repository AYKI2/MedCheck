package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByAccountId(Long id);
}
