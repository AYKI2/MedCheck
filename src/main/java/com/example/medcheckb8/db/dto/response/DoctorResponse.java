package com.example.medcheckb8.db.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse{
        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private String image;
        private String description;
        private String departmentName;
        private Long departmentId;
}
