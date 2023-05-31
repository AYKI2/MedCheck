package com.example.medcheckb8.db.repository;

import org.springframework.stereotype.Repository;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.AppointmentResponse(" +
            "a.id,a.doctor.id,concat(a.doctor.firstName,' ', a.doctor.lastName), " +
            "a.doctor.image, a.doctor.position, a.dateOfVisit, a.status) " +
            "from Appointment a where a.user.account.email=:email")
    List<AppointmentResponse> getUserAppointments(String email);

    @Query("select a from Appointment a where " +
            "a.fullName ilike concat('%' ,:word, '%') " +
            "or a.doctor.firstName ilike concat('%' ,:word, '%') " +
            "or a.doctor.lastName ilike concat('%' ,:word, '%')")
    List<Appointment> findAll(String word);
}
