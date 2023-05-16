package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
@CrossOrigin
@Tag(name = "Result", description = "The result API Endpoint")
public class ResultApi {
    private final ResultService resultService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "The add result method", description = "This method should be used to add a patient result")
    public SimpleResponse addResult(@RequestBody ResultRequest request){
        return resultService.addResult(request);
    }

    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @GetMapping
    @Operation(summary = "Get result method", description = "You can get result by order number")
    public ResultResponse getResult(@RequestParam(required = false) String orderNumber){
        return resultService.getResult(orderNumber);
    }
}
