package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.SampleResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;

import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final JdbcTemplate template;

    @Override
    public List<ScheduleResponse> getAllSchedule(String word) {
        String sql = "SELECT s.id as scheduleId,\n" +
                "        srd.repeat_day_key as repeatDay ,\n" +
                "       sdt.time_from as timeFrom,\n" +
                "       sdt.time_to as timeTo,\n" +
                "       sdt.date as d,\n" +
                "       d.position as position,\n" +
                "       concat(d.first_name, ' ', d.last_name) as full_name,\n" +
                "       d.image as image\n" +
                "         FROM schedules s\n" +
                "         JOIN schedule_date_and_times sdt ON s.id = sdt.id\n" +
                "         JOIN doctors d ON d.position = d.position AND d.first_name = d.first_name and d.last_name = d.last_name\n" +
                "         JOIN schedule_repeat_day srd on s.id = srd.schedule_id where d.position ilike  ? or d.first_name ilike ? or d.last_name ilike ?  ";
        return template.query(sql, new Object[]{"%" + word + "%", "%" + word + "%", "%" + word + "%"}, (resultSet, i) -> {
            Map<LocalTime, LocalTime> times = new HashMap<>();
            times.put(resultSet.getTime("timeFrom").toLocalTime(),
                    resultSet.getTime("timeTo").toLocalTime());
            return new ScheduleResponse(
                    resultSet.getLong("scheduleId"),
                    Repeat.values()[resultSet.getInt("repeatDay")],
                    resultSet.getDate("d").toLocalDate().getMonth(),
                    resultSet.getString("position"),
                    resultSet.getString("full_name"),
                    resultSet.getString("image"),
                    times);
        });

    }

    @Override
    public SampleResponse getDateAndTime() {


        return null;
    }
}
