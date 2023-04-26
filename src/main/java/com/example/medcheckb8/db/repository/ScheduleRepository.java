package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.ScheduleResponse(s.id,s.repeatDay,?,c.date,d.position,concat(d.firstName ,' ',d.lastName),d.image )from Schedule s join ScheduleDateAndTime c join Doctor d ")
    List<ScheduleResponse> getAllScheduleBySearch();


}