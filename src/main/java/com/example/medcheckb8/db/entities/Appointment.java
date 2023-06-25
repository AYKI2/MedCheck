package com.example.medcheckb8.db.entities;

import com.example.medcheckb8.db.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_gen")
    @SequenceGenerator(name = "appointment_gen", sequenceName = "appointment_seq", allocationSize = 1, initialValue = 9)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate dateOfVisit;
    private LocalTime timeOfVisit;
    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private User user;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Doctor doctor;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Department department;
}