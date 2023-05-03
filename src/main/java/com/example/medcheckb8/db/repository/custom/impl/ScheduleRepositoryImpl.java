package com.example.medcheckb8.db.repository.custom.impl;


import com.example.medcheckb8.db.dto.response.ScheduleResponse;

import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.repository.custom.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private final JdbcTemplate template;

    @Override
    public List<ScheduleResponse> getAll(String word, LocalDate start, LocalDate end) {
        String sql = "SELECT s.id as scheduleId, " +
                "        srd.repeat_day_key as repeatDay , " +
                "       sdt.time_from as timeFrom, " +
                "       sdt.time_to as timeTo, " +
                "       sdt.date as date, " +
                "       d.position as position, " +
                "       concat(d.first_name, ' ', d.last_name) as full_name, " +
                "       d.image as image " +
                "         FROM schedules s " +
                "         LEFT JOIN schedule_date_and_times sdt ON sdt.schedule_id = s.id" +
                "         LEFT JOIN doctors d ON d.id = s.doctor_id " +
                "        LEFT JOIN schedule_repeat_day srd on s.id = srd.schedule_id where d.is_active is true %s %s";

        String keyWordCondition = "";
        if (word != null) {
            keyWordCondition = String.format("AND (d.first_name ilike '%s' or d.last_name ilike '%s' or d.position ilike '%s')",
                    "%" + word + "%", "%" + word + "%", "%" + word + "%");
        }

        String dateCondition = "";
        if (start != null && end != null) {
            dateCondition = "AND sdt.date BETWEEN '%s' AND '%s' ".formatted(start, end);
        } else if (start != null) {
            dateCondition = "AND sdt.date >= '%s' ".formatted(start);
        } else if (end != null) {
            dateCondition = "AND sdt.date <= '%s' ".formatted(end);
        }

        sql = String.format(sql, keyWordCondition, dateCondition);

        return template.query(sql, (resultSet, i) -> {
            Map<LocalTime,LocalTime> times = new HashMap<>();
            times.put(resultSet.getTime("timeFrom").toLocalTime(),
                    resultSet.getTime("timeTo").toLocalTime());
            return new ScheduleResponse(
                    resultSet.getLong("scheduleId"),
                    Repeat.values()[resultSet.getInt("repeatDay")],
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("position"),
                    resultSet.getString("full_name"),
                    resultSet.getString("image"),
                    times);
        });
    }

    @Override
    public List<ScheduleResponse> getAll() {
        String sql = "SELECT s.id as scheduleId," +
                "       srd.repeat_day_key as repeatDay ," +
                "       sdt.time_from as timeFrom," +
                "       sdt.time_to as timeTo," +
                "       sdt.date as date," +
                "       d.position as position," +
                "       concat(d.first_name, ' ', d.last_name) as full_name," +
                "       d.image as image " +
                "         FROM schedules s " +
                "         JOIN schedule_date_and_times sdt ON s.id = sdt.id" +
                "         JOIN doctors d ON d.position = d.position AND d.first_name = d.first_name and d.last_name = d.last_name" +
                "         JOIN schedule_repeat_day srd on s.id = srd.schedule_id  ";
        return template.query(sql, (resultSet, i) -> {
            Map<LocalTime, LocalTime> times = new HashMap<>();
            times.put(resultSet.getTime("timeFrom").toLocalTime(),
                    resultSet.getTime("timeTo").toLocalTime());
            return new ScheduleResponse(
                    resultSet.getLong("scheduleId"),
                    Repeat.values()[resultSet.getInt("repeatDay")],
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("position"),
                    resultSet.getString("full_name"),
                    resultSet.getString("image"),
                    times);

        });

    }

}
