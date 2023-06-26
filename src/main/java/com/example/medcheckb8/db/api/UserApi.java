package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.ProfileResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.dto.response.UserGetResultResponse;
import com.example.medcheckb8.db.dto.response.UserResponse;
import com.example.medcheckb8.db.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
@Tag(name = "User", description = "API endpoints for managing users profile3")
@CrossOrigin
public class UserApi {
    private final UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "Получить всех пациентов.",
            description = "С помощью этого метода администратор может получить всех пациентов. Только для администратора.")
    public List<UserResponse> getAllPatients(@RequestParam(required = false) String word) {
        return service.getAllPatients(word);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('PATIENT','ADMIN')")
    @Operation(
            summary = "Метод обновления профиля",
            description = "Профиль пользователя, который может просматривать собственную информацию или изменять ее"
    )
    public SimpleResponse update(@RequestBody @Valid ProfileRequest request) {
        return service.getProfile(request);
    }

    @GetMapping("/getResult")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @Operation(summary = "Метод отправки информации",
            description = "Информация для автозаполнения")    public ProfileResponse getResult() {
        return service.getResult();
    }

    @DeleteMapping("/")
    @Operation(summary = "Метод удаления пациента",
            description = "Этот метод следует использовать для удаления пациента")
    public SimpleResponse deleteById(@RequestParam Long id) {
        return service.deleteById(id);
    }
    @GetMapping("/{patientId}")
    @Operation(summary = "Метод нахождения пациента",
            description = "Этот метод следует использовать для нахождения пациента по ID")
    public UserGetResultResponse findById(@PathVariable Long patientId) {
        return service.findById(patientId);
    }
}
