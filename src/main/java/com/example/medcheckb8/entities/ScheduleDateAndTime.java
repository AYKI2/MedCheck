package com.example.medcheckb8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "schedule_date_and_time")
@NoArgsConstructor
public class ScheduleDateAndTime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_date_and_time_gen")
    @SequenceGenerator(name = "schedule_date_and_time_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate date;
    private LocalTime timeFrom;
    private LocalDate timeTo;
    @ManyToOne(cascade = {PERSIST,DETACH,MERGE,REFRESH})
    Schedule schedule;

}