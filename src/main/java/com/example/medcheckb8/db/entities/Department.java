package com.example.medcheckb8.db.entities;

import com.example.medcheckb8.db.enums.Detachment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    @SequenceGenerator(name = "department_gen", sequenceName = "department_seq", allocationSize = 1, initialValue = 23)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Detachment name;
    @OneToMany(mappedBy = "department", cascade = ALL)
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "department", cascade = ALL)
    private List<Doctor> doctors;
}