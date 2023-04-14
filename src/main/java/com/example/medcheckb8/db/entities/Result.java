package com.example.medcheckb8.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "results")
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_gen")
    @SequenceGenerator(name = "result_gen", sequenceName = "result_seq")
    private Long id;
    private LocalDateTime dateOfIssue;
    private String orderNumber;
    private String file;
    @ManyToOne(cascade = ALL)
    private User user;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Department department;
}