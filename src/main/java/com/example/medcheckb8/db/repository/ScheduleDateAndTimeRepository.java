package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleDateAndTimeRepository extends JpaRepository<ScheduleDateAndTime, Long> {

    @Query("select s.isBusy from ScheduleDateAndTime s where s.schedule.id = :scheduleId and s.date = :date and s.timeFrom = :time")
    Boolean booked(Long scheduleId, LocalDate date, LocalTime time);

    @Query("SELECT new com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse(s.id,s.date,s.timeFrom,s.timeTo,s.isBusy) FROM ScheduleDateAndTime s "
            + "JOIN s.schedule sch "
            + "JOIN sch.doctor doc "
            + "JOIN doc.department dep "
            + "WHERE doc.id = :doctorId and s.date = :date order by s.id"
    )
    List<ScheduleDateAndTimeResponse> findDatesByDoctorIdAndDate(Long doctorId, LocalDate date);

    @Query("select new com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse(s.id,s.date,s.timeFrom,s.timeTo,s.isBusy) from ScheduleDateAndTime s " +
            "where s.schedule.doctor.id = :doctorId and s.date >= :now order by s.date, s.timeFrom asc")
    List<ScheduleDateAndTimeResponse> findScheduleDateAndTimesByScheduleId(Long doctorId, LocalDate now);

    @Query("select count(s) from ScheduleDateAndTime s where s.schedule.doctor.id = :doctorId and s.date = :date and s.isBusy = false")
    int everyoneIsBusy(Long doctorId, LocalDate date);

    @Query("select s from ScheduleDateAndTime s where s.schedule.doctor.id = :doctorId and s.date = :date and s.timeFrom = :time")
    ScheduleDateAndTime findByDateAndTime(Long doctorId, LocalDate date, LocalTime time);
}