package com.example.medcheckb8.db.entities;

import com.example.medcheckb8.db.enums.Repeat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "schedules")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_gen")
    @SequenceGenerator(name = "schedule_gen", sequenceName = "schedule_seq", allocationSize = 1, initialValue = 8)
    private Long id;
    private LocalDate dataOfStart;
    private LocalDate dataOfFinish;
    private int intervalOfHours;
    private LocalTime startBreak;
    private LocalTime endBreak;
    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Repeat, Boolean> repeatDay;
    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Doctor doctor;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Department department;
    @OneToMany(cascade = ALL, mappedBy = "schedule")
    List<ScheduleDateAndTime> dateAndTimes;
}