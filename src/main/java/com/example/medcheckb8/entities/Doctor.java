package com.example.medcheckb8.entities;

import com.example.medcheckb8.enums.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "doctors")
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
    @SequenceGenerator(name = "doctor_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private Position position;
    private Boolean isActive;
    private String image;
    private String description;
    @OneToMany(mappedBy = "doctor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Appointment> appointments ;

    @ManyToOne(cascade ={PERSIST,
            REFRESH,
            MERGE,
            DETACH})
    private Department department;
    @OneToMany(mappedBy = "doctor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Schedule> schedules ;

}