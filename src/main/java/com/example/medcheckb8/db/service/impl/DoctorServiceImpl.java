package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.request.DoctorRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

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
    public List<DoctorResponse> searchByFirstNameOrLastNameOrDepartment(String keyWord) {
        return doctorRepository.searchByFirstNameOrLastNameOrDepartment(keyWord);
    }
}
