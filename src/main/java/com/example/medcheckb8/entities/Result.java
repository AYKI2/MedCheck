package com.example.medcheckb8.entities;

import jakarta.persistence.*;
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
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime dateOdIssue;
    private String orderNumber;
    private String file;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(cascade = {PERSIST,
            MERGE,
            REFRESH,
            DETACH})
    private Department department;

}