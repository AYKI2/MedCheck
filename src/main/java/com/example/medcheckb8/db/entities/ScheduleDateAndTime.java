package com.example.medcheckb8.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "schedule_date_and_times")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDateAndTime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_date_and_time_gen")
    @SequenceGenerator(name = "schedule_date_and_time_gen", sequenceName = "schedule_date_time_day_seq", allocationSize = 1, initialValue = 351)
    private Long id;
    private LocalDate date;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private Boolean isBusy;
    @ManyToOne(cascade = {PERSIST, DETACH, MERGE, REFRESH})
    private Schedule schedule;
}