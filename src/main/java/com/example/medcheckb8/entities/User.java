package com.example.medcheckb8.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<Appointment> appointments ;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<Result> results ;
}