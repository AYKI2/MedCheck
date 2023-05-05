package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.DepartmentResponse;
import com.example.medcheckb8.db.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
@Tag(name = "Department CRUD", description = "API endpoints for departments")
public class DepartmentApi {
    private final DepartmentService departmentService;
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping("/getAll")
    @Operation(
            summary = "The departments get all method",
            description = "All departments page"
    )
    List<DepartmentResponse>getAll(){
        return departmentService.getAllDepartment();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping("/{departmentId}")
    @Operation(
            summary = "The departments get by id method",
            description = "Departments get by id"
    )
    DepartmentResponse findById( @PathVariable Long departmentId){
        return departmentService.findById(departmentId);
    }
}
