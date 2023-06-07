package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.*;
import com.example.medcheckb8.db.dto.response.appointment.ScheduleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.repository.ScheduleDateAndTimeRepository;
import com.example.medcheckb8.db.service.DoctorService;
import com.example.medcheckb8.db.utill.ExportToExcel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final ScheduleDateAndTimeRepository scheduleDateAndTimeRepository;
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = Logger.getLogger(Doctor.class.getName());

    @Override
    public SimpleResponse save(DoctorSaveRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NotFountException(
                        String.format("Department with id: %d not found", request.departmentId())
                ));

        Doctor doctor = Doctor.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .position(request.position())
                .image(request.image())
                .department(department)
                .description(request.description())
                .isActive(false)
                .build();

        department.addDoctor(doctor);
        doctorRepository.save(doctor);
        logger.info("Saved doctor with full name: {} {}" + doctor.getFirstName() + doctor.getLastName());


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with full name: %s %s Successfully saved",
                        doctor.getFirstName(), doctor.getLastName()))
                .build();
    }

    @Override
    public DoctorResponse findById(Long id) {
        logger.info("Finding doctor with ID: {}" + id);
        return doctorRepository.findByDoctorId(id).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d doesn't exist", id)
        ));
    }

    @Override
    public List<ExpertResponse> getAllWithSearchExperts(String keyWord) {
        return doctorRepository.getAllWithSearch(keyWord);
    }

    @Override
    public SimpleResponse update(DoctorUpdateRequest request) {
        try {
            Doctor doctor = doctorRepository.findById(request.doctorId()).orElseThrow(() -> new NotFountException(
                    String.format("Doctor with id: %d not found.", request.doctorId())
            ));
            Department department = departmentRepository.findById(request.departmentId()).orElseThrow(() -> new NotFountException(
                    String.format("Department with id: %d not found.", request.doctorId())
            ));

            doctor.setFirstName(request.firstName());
            doctor.setLastName(request.lastName());
            doctor.setImage(request.image());
            doctor.setDescription(request.description());
            doctor.setDepartment(department);
            doctor.setPosition(request.position());

            doctorRepository.save(doctor);
            logger.info(String.format("Doctor with id: %d Successfully updated.", doctor.getId()));

            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("Doctor with id: %d Successfully updated.", doctor.getId()))
                    .build();
        } catch (Exception e) {
            logger.severe("Error updating doctor: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        logger.info(String.format("Attempting to delete doctor with id: %d", id));
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d not found.", id)
        ));
        doctorRepository.delete(doctor);
        logger.info(String.format("Doctor with id: %d successfully deleted", id));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with id: %d Successfully deleted.", id))
                .build();
    }

    @Override
    public SimpleResponse activateAndDeactivateDoctor(Boolean isActive, Long doctorId) {
        logger.info(String.format("Activating/deactivating doctor with id %s", doctorId));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d not found.", doctorId)
        ));

        doctor.setIsActive(isActive);
        doctorRepository.save(doctor);

        if (!isActive) {
            logger.info(String.format("Doctor with id %s has been deactivated", doctorId));
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("Doctor with id: %s is deactivated!", doctor.getId()))
                    .build();
        }
        logger.info(String.format("Doctor with id %s has been activated", doctorId));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with id: %s is activated!", doctor.getId()))
                .build();
    }

    @Override
    public List<ScheduleResponse> findDoctorsByDate(String department, String timeZone) {
        try {
            logger.log(Level.INFO, "Finding doctors by date for department: {0}", department);
            ZoneId zoneId = ZoneId.of(timeZone);
            LocalDateTime currentTime = LocalDateTime.now(zoneId);
            List<ScheduleResponse> responses = new ArrayList<>();
            List<Doctor> doctors = doctorRepository.findByDepartmentName(department);
            List<ScheduleDateAndTimeResponse> scheduleDateAndTimeResponses = new ArrayList<>();
            LocalDate currentDate = null;
            boolean isNext = true;
            boolean isCurrent = true;
            int everyoneIsBusy = -1;
            long i = 0;
            LocalTime nextTime = currentTime.toLocalTime();
            for (Doctor doctor : doctors) {
                List<ScheduleDateAndTimeResponse> dateAndTimes = scheduleDateAndTimeRepository.findScheduleDateAndTimesByScheduleId(doctor.getId(), currentTime.toLocalDate());
                for (ScheduleDateAndTimeResponse dateAndTime : dateAndTimes) {
                    if (isCurrent) {
                        if (currentDate == null) {
                            currentDate = dateAndTime.date();
                        }
                        everyoneIsBusy = scheduleDateAndTimeRepository.everyoneIsBusy(doctor.getId(), currentDate);
                        isCurrent = false;
                        if (!dateAndTime.isBusy() && dateAndTime.timeFrom().isAfter(currentTime.toLocalTime())) {
                            scheduleDateAndTimeResponses.add(dateAndTime);
                        }
                    } else if (everyoneIsBusy == 0) {
                        everyoneIsBusy = -1;
                        i = currentDate.getDayOfMonth();
                        isNext = true;
                        scheduleDateAndTimeResponses.clear();
                    } else if (dateAndTime.date().getDayOfMonth() > i
                            && currentDate.getDayOfMonth() == dateAndTime.date().getDayOfMonth()
                    ) {
                        if (dateAndTime.timeFrom().isAfter(nextTime)) {
                            List<ScheduleDateAndTimeResponse> dates = scheduleDateAndTimeRepository.findDatesByDoctorIdAndDate(doctor.getId(), currentDate);
                            if (currentTime.toLocalTime().isBefore(dateAndTime.timeFrom())
                                    && dateAndTime.isBusy()) {
                                continue;
                            }
                            scheduleDateAndTimeResponses.addAll(dates);
                            isNext = true;
                            break;
                        }
                    } else if (isNext && scheduleDateAndTimeResponses.isEmpty()) {
                        currentDate = currentDate.plusDays(1L);
                        nextTime = LocalTime.MIN;
                        isNext = false;
                        isCurrent = true;
                    }
                }
                ScheduleResponse build = ScheduleResponse.builder()
                        .id(doctor.getId())
                        .fullName(doctor.getLastName() + " " + doctor.getFirstName())
                        .image(doctor.getImage())
                        .position(doctor.getPosition())
                        .localDateTimes(scheduleDateAndTimeResponses)
                        .build();
                responses.add(build);
            }
            logger.log(Level.INFO, "Found {0} doctors by date for department: {1}", new Object[]{responses.size(), department});
            return responses;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while finding doctors by date for department: " + department, e);
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<DoctorExportResponse> exportDoctorToExcel(HttpServletResponse response) throws IOException {
        Instant start = Instant.now();
        String sql = """
                SELECT d.id as doctorId,
                d.first_name as firstName,
                d.last_name as lastName,
                d.position as position,
                sh.data_of_start as dataOfStart,
                sh.data_of_finish as dataOfFinish,
                sdt.date as data,
                sdt.time_from as timeFrom,
                sdt.time_to as timeTo,
                sdt.is_busy as isBusy
                FROM doctors d
                JOIN schedules sh ON d.id = sh.doctor_id
                JOIN schedule_date_and_times sdt ON sh.id = sdt.schedule_id
                """;
        try {
            List<DoctorExportResponse> doctors = jdbcTemplate.query(sql, (resultSet, i) -> {
                Map<LocalTime, LocalTime> times = new HashMap<>();
                times.put(resultSet.getTime("timeFrom").toLocalTime(),
                        resultSet.getTime("timeTo").toLocalTime());

                return new DoctorExportResponse(
                        resultSet.getLong("doctorId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("position"),
                        resultSet.getDate("dataOfStart").toLocalDate(),
                        resultSet.getDate("dataOfFinish").toLocalDate(),
                        resultSet.getDate("data").toLocalDate(),
                        times,
                        resultSet.getBoolean("isBusy"));
            });
            ExportToExcel exportToExcel = new ExportToExcel(doctors);
            exportToExcel.exportDataToExcel(response);
            logger.info(String.format("Successfully exported %d doctors to Excel. Execution time: %d ms", doctors.size(), Duration.between(start, Instant.now()).toMillis()));
        } catch (Exception e) {
            logger.severe("Error exporting doctors to Excel: " + e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public List<OurDoctorsResponse> findByDepartmentName(String name) {
        return doctorRepository.findDoctorByDepartmentName(name);
    }
}
