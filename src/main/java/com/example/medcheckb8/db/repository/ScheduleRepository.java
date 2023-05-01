package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.SampleResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponseForSearch;
import com.example.medcheckb8.db.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//    @Query("select new com.example.medcheckb8.db.dto.response.ScheduleResponse(s.id,s.repeatDay,c.date,concat(d.firstName, ' ',d.lastName ) ,d.position,d.image)from ScheduleResponse s  where s.date = :dateValue and :timeValue between key(s.times) and value(s.times)\n")
//    List<ScheduleResponse> getAllSearch(String word);
//    SampleResponse getDateAndTime();
}