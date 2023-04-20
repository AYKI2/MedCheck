package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ResultApi {
    private final ResultService resultService;

    @PostMapping("/add/{patientId}")
    public SimpleResponse addResult(@RequestBody ResultRequest request,
                                    @PathVariable Long patientId){
        return resultService.addResult(patientId, request);
    }
}
