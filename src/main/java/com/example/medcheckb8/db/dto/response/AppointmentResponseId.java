package com.example.medcheckb8.db.dto.response;

import com.example.medcheckb8.db.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseId {
        private Long appointmentId;
        private Long doctorId;
        private String patientFirstName;
        private String patientLastName;
        private String doctorFullName;
        private String email;
        private String phoneNumber;
        private LocalDate date;
        private LocalTime time;
        private Status status;
        private String departmentName;
}
