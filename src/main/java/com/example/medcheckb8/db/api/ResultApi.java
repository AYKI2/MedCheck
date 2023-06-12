package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ResultRequest;
import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.dto.response.UserResultResponse;
import com.example.medcheckb8.db.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
@CrossOrigin
@Tag(name = "Result", description = "The result API Endpoint")
public class ResultApi {
    private final ResultService resultService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Метод добавления результата",
            description = "Этот метод следует использовать для добавления результата пациента.")
    public UserResultResponse addResult(@RequestBody ResultRequest request){
        return resultService.addResult(request);
    }

    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @GetMapping
    @Operation(summary = "Метод получения результата",
            description = "Вы можете получить результат по номеру заказа.")
    public ResultResponse getResult(@RequestParam(required = false) String orderNumber){
        return resultService.getResult(orderNumber);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{patientId}")
    @Operation(summary = "Метод получения результатов пациента",
            description = "Вы можете получить результаты по идентификатору пациента. Только для администратора.")
    public List<UserResultResponse> getResultByPatientId(@PathVariable Long patientId){
        return resultService.getResultByUserId(patientId);
    }
}
