package com.example.medcheckb8.db.dto.response;

import lombok.*;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpertResponse {
    private Long id;
    private Boolean isActive;
    private String firstName;
    private String lastName;
    private String position;
    private String image;
    private String departmentName;
    private LocalDate dataOfFinish;
}
