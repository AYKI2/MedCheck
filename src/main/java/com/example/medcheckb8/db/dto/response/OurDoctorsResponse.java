package com.example.medcheckb8.db.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OurDoctorsResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String image;
    private String departmentName;
}
