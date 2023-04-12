package com.example.medcheckb8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "departments")
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    @SequenceGenerator(name = "department_seq",allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "department",
            cascade = ALL)
    private List<Doctor> doctors;



}