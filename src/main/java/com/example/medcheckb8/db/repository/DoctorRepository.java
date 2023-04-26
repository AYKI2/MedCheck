package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.DoctorResponse(d.id, d.firstName, d.lastName, d.position," +
            " d.image, d.description, d.department.name) " +
            "from Doctor d")
    List<DoctorResponse> getAll();

    @Query("select new com.example.medcheckb8.db.dto.response.DoctorResponse(d.id, d.firstName, d.lastName, d.position," +
            " d.image, d.description, d.department.name) " +
            "from Doctor d where d.id=?1")
    Optional<DoctorResponse> findByDoctorId(Long id);

    @Query("select d from Doctor d where lower(d.department.name) = lower(?1)")
    List<Doctor> findByDepartmentName(String department);

    @Query("select new com.example.medcheckb8.db.dto.response.ScheduleResponse(concat(d.firstName,' ',d.lastName), d.image, d.position, " +
            "(select new com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse(s.date,s.timeFrom,s.timeTo,s.isBusy) " +
            "FROM ScheduleDateAndTime s where s.schedule.id = d.schedule.id)) from Doctor d " +
            "where lower(d.department.name) = lower(:department) and d.schedule.dataOfStart <= :localDate and d.schedule.dataOfFinish >= :localDate")
    List<ScheduleResponse> getAllByDepartmentName(String department, LocalDate localDate);
}