package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDateAndTimeRepository extends JpaRepository<ScheduleDateAndTime, Long> {
    @Query("SELECT NEW com.example.medcheckb8.db.dto.response.ScheduleDateAndTimeResponse(s.date,s.timeFrom,s.timeTo,s.isBusy) FROM ScheduleDateAndTime s where s.schedule.id = :scheduleId and s.date = :localDate")
    List<ScheduleDateAndTimeResponse> getAllByScheduleId(Long scheduleId, LocalDate localDate);
}