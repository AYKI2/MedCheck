package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleDateAndTimeRepository extends JpaRepository<ScheduleDateAndTime, Long> {

    @Query("select s.isBusy from ScheduleDateAndTime s where s.schedule.id = :scheduleId and s.date = :date and s.timeFrom = :time")
    Boolean booked(Long scheduleId, LocalDate date, LocalTime time);

    @Query("SELECT sdt FROM ScheduleDateAndTime sdt "
            + "JOIN sdt.schedule sch "
            + "JOIN sch.doctor doc "
            + "JOIN doc.department dep "
            + "WHERE doc.id = :doctorId "
    )
    List<ScheduleDateAndTime> findAvailableDatesByDepartmentAndDate(Long doctorId);
}