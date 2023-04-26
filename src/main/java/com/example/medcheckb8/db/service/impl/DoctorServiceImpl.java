package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.entities.ScheduleDateAndTime;
import com.example.medcheckb8.db.enums.Repeat;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.repository.ScheduleDateAndTimeRepository;
import com.example.medcheckb8.db.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final ScheduleDateAndTimeRepository scheduleDateAndTimeRepository;

    @Override
    public SimpleResponse save(Long departmentId, DoctorRequest request) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFountException(
                        String.format("Department with id: %d not found", departmentId)
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
    public SimpleResponse update(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new NotFountException(
                String.format("Doctor with id: %d not found.", id)
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
    public Boolean booked(Long doctorId, LocalDateTime localDateTime) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFountException(String.format("Doctor with id: %d not found.", doctorId)));
        String displayName = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
        System.out.println(displayName);
        if (!doctor.getIsActive() && !doctor.getSchedule().getRepeatDay().get(Repeat.valueOf(displayName))) {
            throw new BadRequestException("This specialist is currently on vacation or not working.");
        }
        for (ScheduleDateAndTime dateAndTime : doctor.getSchedule().getDateAndTimes()) {
            if (dateAndTime.getDate().equals(localDateTime.toLocalDate())
                    && dateAndTime.getTimeFrom().equals(localDateTime.toLocalTime())) {
                if(!dateAndTime.getIsBusy()) {
                    dateAndTime.setIsBusy(true);
                    return false;
                }else {
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<ScheduleResponse> freeSpecialists(String department, LocalDate localDate) {
        List<Doctor> freeDoctors = doctorRepository.findByDepartmentName(department);
        List<ScheduleResponse> responses = new ArrayList<>();
        if (freeDoctors.isEmpty()) {
            throw new NotFountException("There are currently no employees in this department!");
        }
        String displayName = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase();
        for (Doctor doctor : freeDoctors) {
            if (doctor.getIsActive() && doctor.getSchedule().getRepeatDay().get(Repeat.valueOf(displayName))) {
                if (doctor.getSchedule().getDataOfStart().isBefore(localDate) && doctor.getSchedule().getDataOfFinish().isAfter(localDate)) {
                    ScheduleResponse schedule = ScheduleResponse.builder()
                            .fullName(doctor.getFirstName() + " " + doctor.getLastName())
                            .image(doctor.getImage())
                            .position(doctor.getPosition())
                            .localDateTimes(scheduleDateAndTimeRepository.getAllByScheduleId(doctor.getSchedule().getId(), localDate))
                            .build();
                    responses.add(schedule);
                }
            }
        }
        return responses;
    }
}
