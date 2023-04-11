package com.example.medcheckb8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "department",
            cascade = ALL,
            orphanRemoval = true)
    private List<Doctor> doctors = new ArrayList<>();



}