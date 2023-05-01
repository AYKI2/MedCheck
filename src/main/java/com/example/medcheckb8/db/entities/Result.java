package com.example.medcheckb8.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "results")
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_gen")
    @SequenceGenerator(name = "result_gen", sequenceName = "result_seq", allocationSize = 1, initialValue = 5)
    private Long id;
    private LocalDateTime dateOfIssue;
    private String orderNumber;
    private String file;
    @ManyToOne(cascade = ALL)
    private User user;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Department department;
}