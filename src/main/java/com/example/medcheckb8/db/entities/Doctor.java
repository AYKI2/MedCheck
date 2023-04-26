package com.example.medcheckb8.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_gen")
    @SequenceGenerator(name = "doctor_gen", sequenceName = "doctor_seq", allocationSize = 1, initialValue = 47)
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Boolean isActive;
    private String image;
    private String description;
    @OneToMany(mappedBy = "doctor", cascade = ALL)
    private List<Appointment> appointments;
    @ManyToOne(cascade = {PERSIST, REFRESH, MERGE, DETACH})
    private Department department;
    @OneToOne(mappedBy = "doctor", cascade = ALL)
    private Schedule schedule;
}