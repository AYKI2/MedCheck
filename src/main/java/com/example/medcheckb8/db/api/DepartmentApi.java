package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.DepartmentResponse;
import com.example.medcheckb8.db.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentApi {
    private final DepartmentService departmentService;
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping("/getAll")
    List<DepartmentResponse>getAll(){
        return departmentService.getAllDepartment();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping("/{departmentId}")
    DepartmentResponse findById( @PathVariable Long departmentId){
        return departmentService.findById(departmentId);
    }
}
