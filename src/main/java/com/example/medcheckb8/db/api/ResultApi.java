package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
public class ResultApi {
    private final ResultService resultService;

    @PostMapping
    public SimpleResponse addResult(@RequestBody ResultRequest request,
                                    @RequestParam Long patientId){
        return resultService.addResult(patientId, request);
    }
}
