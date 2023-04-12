package com.example.medcheckb8.entities;

import com.example.medcheckb8.enums.Repeat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "schedules")
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_gen")
    @SequenceGenerator(name = "schedule_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate dataOfStart;
    private LocalDate dataOfFinish;
    private int intervalOfHours;
    @ElementCollection
    private Map<Repeat,Boolean>repeatDay;
    @ManyToOne(cascade = CascadeType.ALL)
    private Doctor doctor   ;
    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "schedule")
    List< ScheduleDateAndTime> dateAndTimes;



}