package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ResultApi {
    private final ResultService resultService;

    @PostMapping("/add")
    public ResultResponse addResult(@RequestBody ResultRequest request){
        return resultService.addResult(request);
    }
}
