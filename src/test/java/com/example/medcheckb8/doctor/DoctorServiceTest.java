package com.example.medcheckb8.doctor;

import com.example.medcheckb8.db.dto.request.DoctorSaveRequest;
import com.example.medcheckb8.db.dto.request.DoctorUpdateRequest;
import com.example.medcheckb8.db.dto.response.DoctorResponse;
import com.example.medcheckb8.db.dto.response.ExpertResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Department;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.enums.Detachment;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.DepartmentRepository;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-kuban.properties")
@ExtendWith(SpringExtension.class)
class DoctorServiceTest {
    @Autowired
    private DoctorServiceImpl doctorService;
    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllWithSearchExperts() {
        //firstName,LastName писать маленкими буквами а услуги(Detachment) большими
        String keyword = "гер";
        ExpertResponse expertRespon = new ExpertResponse(111L, true, "Акыл", "торин", "global", "image", Detachment.ALLERGOLOGY, LocalDate.now());
        ExpertResponse expertRespon2 = new ExpertResponse(101L, true, "Геракыл", "торин", "global", "image", Detachment.DERMATOLOGY, LocalDate.now());
        List<ExpertResponse> expectedExperts = Arrays.asList(expertRespon, expertRespon2);

        when(doctorRepository.getAllWithSearch(anyString())).thenReturn(expectedExperts);


        List<ExpertResponse> experts = doctorService.getAllWithSearchExperts(keyword);

        assertNotNull(experts);
        assertFalse(experts.isEmpty());
        assertEquals(2, experts.size());
        assertEquals(expertRespon, experts.get(0));
    }

    @Test
    void testSave_Successful() {
        long departId = 1L;
        Department department = new Department();
        when(departmentRepository.findById(departId)).thenReturn(Optional.of(department));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(new Doctor());

        var expert = new DoctorSaveRequest(departId,"Kuban", "Kelsinbekov", "Medic","Image",
                 "Good Doctor");

        SimpleResponse request = doctorService.save(expert);

        assertNotNull(request);
        assertEquals("Kuban", expert.firstName());
        assertEquals(HttpStatus.OK, request.status());
        assertEquals("Doctor with full name: Kuban Kelsinbekov Successfully saved", request.message());

        verify(departmentRepository, times(1)).findById(departId);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }
    @Test
    void testSave_DepartmentNotFound(){
        long departmentId = 1L;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        var doctor = new DoctorSaveRequest(departmentId,"Kuban", "Kelsinbekov", "Medic","Image",
                "Good Doctor");

        assertThrows(NotFountException.class, () -> doctorService.save(doctor));

        verify(departmentRepository, times(1)).findById(departmentId);
        verify(doctorRepository, never()).save(any(Doctor.class));
    }


    @Test
    void testFindById(){
        Long id = 1L;
        DoctorResponse doctorResponse = new DoctorResponse(
                id, "Kuban", "Kelsinbekov", "doctor", "image", "good", Detachment.ALLERGOLOGY,1L
        );

        when(doctorRepository.findByDoctorId(id)).thenReturn(Optional.of(doctorResponse));

        DoctorResponse result = doctorService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Kuban", result.firstName());
        assertEquals("Kelsinbekov", result.lastName());

        verify(doctorRepository).findByDoctorId(id);
    }


    @Test
    void testUpdate () {
        var request = new DoctorUpdateRequest(
                1L, "Kuban", "Kelsinbekov", "doctor", "image", "good");

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Mails");
        doctor.setLastName("Morales");


        when(doctorRepository.findById(request.doctorId())).thenReturn(Optional.of(doctor));

        SimpleResponse result = doctorService.update(request);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.status());
        assertEquals("Doctor with id: 1 Successfully updated.", result.message());

        assertEquals(request.firstName(), doctor.getFirstName());
        assertEquals(request.lastName(), doctor.getLastName());
        assertEquals(request.image(), doctor.getImage());
        assertEquals(request.description(), doctor.getDescription());

        verify(doctorRepository).findById(request.doctorId());
        verify(doctorRepository).save(doctor);
    }


    @Test
    void testDelete() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setFirstName("Mails");
        doctor.setLastName("Morales");

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));

        SimpleResponse result = doctorService.delete(id);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.status());
        assertEquals("Doctor with id: 1 Successfully deleted.", result.message());

        verify(doctorRepository).findById(id);
        verify(doctorRepository).delete(doctor);
    }

}
