package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.AppointmentResponse(" +
            "concat(a.doctor.firstName,' ', a.doctor.lastName), " +
            "a.doctor.image, a.doctor.position, a.dateOfVisit, a.status) " +
            "from Appointment a where a.user.account.email=:email")
    List<AppointmentResponse> getUserAppointments(String email);
}
