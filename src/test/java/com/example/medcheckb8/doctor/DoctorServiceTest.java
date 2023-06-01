package com.example.medcheckb8.doctor;

import com.example.medcheckb8.db.dto.response.ExpertResponse;
import com.example.medcheckb8.db.enums.Detachment;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-uluk.properties")
class DoctorServiceTest {
    @Autowired
    private DoctorServiceImpl doctorService;

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
        ExpertResponse expertRespon = new ExpertResponse(111L,true,"Акыл","торин","global","image", Detachment.ALLERGOLOGY, LocalDate.now());
        ExpertResponse expertRespon2 = new ExpertResponse(101L,true,"Геракыл","торин","global","image", Detachment.DERMATOLOGY, LocalDate.now());
        List<ExpertResponse> expectedExperts = Arrays.asList(expertRespon, expertRespon2);

        when(doctorRepository.getAllWithSearch(anyString())).thenReturn(expectedExperts);


        List<ExpertResponse> experts = doctorService.getAllWithSearchExperts(keyword);

        assertNotNull(experts);
        assertFalse(experts.isEmpty());
        assertEquals(1, experts.size());
        assertEquals(expertRespon2, experts.get(0));
    }

}
