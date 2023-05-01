package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorExportResponse;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.service.DoctorService;
import com.example.medcheckb8.db.utill.ExportToExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    private final JdbcTemplate jdbcTemplate;

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

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with full name: %s %s Successfully saved",
                        doctor.getFirstName(), doctor.getLastName()))
                .build();
    }

    @Override
    public DoctorResponse findById(Long id) {
        return doctorRepository.findByDoctorId(id).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d doesn't exist", id)
        ));
    }

    @Override
    public List<DoctorResponse> getAll() {
        return doctorRepository.getAll();
    }

    @Override
    public SimpleResponse update(DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(request.doctorId()).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d not found.", request.doctorId())
        ));

        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        doctor.setImage(request.image());
        doctor.setDescription(request.description());

        doctorRepository.save(doctor);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with id: %d Successfully updated.", doctor.getId()))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new NotFountException(String.format("Doctor with id: %d doesnt exist.", id));
        }
        doctorRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with id: %d Successfully deleted.", id))
                .build();
    }

    @Override
    public SimpleResponse activateAndDeactivateDoctor(Boolean isActive, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d not found.", doctorId)
        ));

        doctor.setIsActive(isActive);
        doctorRepository.save(doctor);

        if (!isActive) {
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("Doctor with id: %s is deactivated!", doctor.getId()))
                    .build();
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Doctor with id: %s is activated!", doctor.getId()))
                .build();
    }

    @Override
    public List<DoctorExportResponse> exportDoctorToExcel(HttpServletResponse response) throws IOException {
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
        return doctors;
    }


}
