package com.example.medcheckb8.db.entities;

import com.example.medcheckb8.db.enums.Repeat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Table(name = "schedules")
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_gen")
    @SequenceGenerator(name = "schedule_gen", sequenceName = "schedule_seq", allocationSize = 1, initialValue = 8)
    private Long id;
    private LocalDate dataOfStart;
    private LocalDate dataOfFinish;
    private int intervalOfHours;
    @ElementCollection
    private Map<Repeat, Boolean> repeatDay;
    @ManyToOne(cascade = ALL)
    private Doctor doctor;
    @ManyToOne(cascade = ALL)
    private Department department;
    @OneToMany(cascade = ALL, mappedBy = "schedule")
    List<ScheduleDateAndTime> dateAndTimes;
}