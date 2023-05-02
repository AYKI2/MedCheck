package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.SampleRequest;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleApi {
    private final ScheduleService service;

    @GetMapping
    public List<ScheduleResponse> getAll(@RequestParam(required = false) String word,
                                         @RequestParam(required = false)LocalDate startDate,
                                         @RequestParam(required = false)LocalDate endDate) {
        return service.getAllSchedule(word,startDate,endDate);
    }
}
