package com.example.medcheckb8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime dateOdIssue;
    private String orderNumber;
    private String file;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = {PERSIST,
            MERGE,
            REFRESH,
            DETACH})
    @JoinColumn(name = "service_id")
    private Department department;

}