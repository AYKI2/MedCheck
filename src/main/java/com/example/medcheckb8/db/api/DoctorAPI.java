package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.*;
import com.example.medcheckb8.db.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
@CrossOrigin
@Tag(name = "doctor", description = "Expert API Endpoints")
public class DoctorAPI {
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Метод сохранения эксперта.",
            description = "Этот метод следует использовать для сохранения эксперта.")
    public SimpleResponse save(@RequestBody @Valid DoctorSaveRequest doctorRequest) {
        return doctorService.save(doctorRequest);
    }

    @GetMapping("/find")
    @Operation(summary = "Метод поиска эксперта.",
            description = "Этот метод следует использовать для поиска эксперта по идентификатору.")
    public DoctorResponse findById(@RequestParam Long doctorId) {
        return doctorService.findById(doctorId);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Метод поиска экспертов",
            description = "С помощью этого метода вы можете найти врача по имени и фамилии, " +
                    "а также найти отделение.")
    List<ExpertResponse> search(@RequestParam(required = false) String keyWord) {
        return doctorService.getAllWithSearchExperts(keyWord);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Метод обновления эксперта.",
            description = "Этот метод следует использовать для обновления эксперта.")
    public SimpleResponse update(@RequestBody @Valid DoctorUpdateRequest doctorRequest) {
        return doctorService.update(doctorRequest);
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Метод удаления эксперта.",
            description = "Этот метод следует использовать для удаления эксперта.")
    public SimpleResponse delete(@RequestParam Long doctorId) {
        return doctorService.delete(doctorId);
    }

    @PostMapping("/isActive")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Метод активации и деактивации эксперта.",
            description = "Этот метод предназначен для активации и деактивации эксперта.")
    public SimpleResponse isActive(@RequestParam Boolean isActive,
                                   @RequestParam Long doctorId) {
        return doctorService.activateAndDeactivateDoctor(isActive, doctorId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/export-to-excel")
    @Operation(summary = "Метод экспорта в Excel",
            description = "С помощью этого метода вы можете экспортировать значения базы данных врачей в Excel."
    )
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Doctors_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        doctorService.exportDoctorToExcel(response);
    }

    @GetMapping("/{departmentName}")
    @Operation(summary = "Метод поиска экспертов по названию отдела.",
            description = "Этот метод следует использовать для поиска экспертов по названию отдела.")
    public List<OurDoctorsResponse> findByName(@PathVariable @NotNull(message = "Название отделения не должно быть пустым!") String departmentName) {
        return doctorService.findByDepartmentName(departmentName.toUpperCase());
    }
}
