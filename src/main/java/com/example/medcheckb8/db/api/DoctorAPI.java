package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ExpertResponse;
import com.example.medcheckb8.db.dto.response.OurDoctorsResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
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
    @Operation(summary = "The expert save method.",
            description = "This method should be used to save the Expert")
    public SimpleResponse save(@RequestBody @Valid DoctorSaveRequest doctorRequest) {
        return doctorService.save(doctorRequest);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert find method.",
            description = "This method should be used to find the Expert by id")
    public DoctorResponse findById(@RequestParam Long doctorId) {
        return doctorService.findById(doctorId);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "The  search method experts", description = "Using the method, you can find" +
            " a doctor by name and surname," +
            " you can find a department")
    List<ExpertResponse> search(@RequestParam(required = false) String keyWord) {
        return doctorService.getAllWithSearchExperts(keyWord);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert update method.",
            description = "This method should be used to update the Expert")
    public SimpleResponse update(@RequestBody @Valid DoctorUpdateRequest doctorRequest) {
        return doctorService.update(doctorRequest);
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert delete method.",
            description = "This method should be used to delete the Expert")
    public SimpleResponse delete(@RequestParam Long doctorId) {
        return doctorService.delete(doctorId);
    }

    @PostMapping("/isActive")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "The expert activate and deactivate method.",
            description = "This method should be used to activate and deactivate the Expert.")
    public SimpleResponse isActive(@RequestParam Boolean isActive,
                                   @RequestParam Long doctorId) {
        return doctorService.activateAndDeactivateDoctor(isActive, doctorId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/export-to-excel")
    @Operation(summary = "The Export to Excel method",
            description = "Using the method, export dataBase doctors values to Excel values."
    )
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Doctors_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        doctorService.exportDoctorToExcel(response);
    }

    @GetMapping("/{departmentName}")
    @Operation(summary = "The expert find by department name.",
            description = "This method should be used to find the Experts by department name")
    public List<OurDoctorsResponse> findByName(@PathVariable @NotNull(message = "Название отделения не должно быть пустым!") String departmentName) {
        return doctorService.findByDepartmentName(departmentName.toUpperCase());
    }
}
