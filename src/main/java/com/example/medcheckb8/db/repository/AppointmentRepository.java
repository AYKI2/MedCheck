package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.AppointmentResponseId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
            "a.doctor.image, a.doctor.position, a.dateOfVisit,a.timeOfVisit, a.status) " +
            "from Appointment a where a.user.account.email=:email")
    List<AppointmentResponse> getUserAppointments(String email);
    @Query("select a from Appointment a where " +
            "(:word is null or :word = '' or " +
            "a.fullName ilike concat('%' ,:word, '%') " +
            "or a.doctor.firstName ilike concat('%' ,:word, '%') " +
            "or a.doctor.lastName ilike concat('%' ,:word, '%'))")
    Page<Appointment> findAll(@Param("word") String word, Pageable pageable);

    @Query("select new com.example.medcheckb8.db.dto.response.AppointmentResponseId(" +
            "a.id, a.doctor.id, a.user.firstName, a.user.lastName, concat(a.doctor.firstName,' ', a.doctor.lastName)," +
            "a.user.account.email, a.user.phoneNumber, a.dateOfVisit, a.timeOfVisit, a.status, cast(a.department.name as string ))" +
            " from Appointment a where a.id=:id")
    AppointmentResponseId findByPatientId(Long id);
}
