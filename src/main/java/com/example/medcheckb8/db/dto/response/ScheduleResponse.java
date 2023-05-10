package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Repeat;
import lombok.Builder;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Builder
public record ScheduleResponse (
     Long id,
     Repeat repeatDay,
     LocalDate date,
     String position,
     String fullName,
     String image,
    Map<LocalTime, LocalTime> times){


}
