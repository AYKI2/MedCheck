package com.example.medcheckb8.result;

import com.example.medcheckb8.db.dto.response.ResultResponse;
import com.example.medcheckb8.db.repository.ResultRepository;
import com.example.medcheckb8.db.service.impl.ResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultServiceTest {

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private ResultServiceImpl resultService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetResult(){
        String orderNumber = "11111111111";
        ResultResponse resultResponse = ResultResponse.builder()
                .patientId(2L)
                .patientFullName("Кубан Келсинбеков")
                .patientEmail("kubanmuit@gmail.com")
                .patientPhoneNumber("+996708281456")
                .dateOfIssue(LocalDate.of(2023, 6, 13))
                .timeOfIssue(LocalTime.of(14,0,0))
                .orderNumber("11111111111")
                .name("ANESTHESIOLOGY")
                .file("https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685539128052hello.docx")
                .build();

        Mockito.when(resultRepository.getResultByOrderNumber(orderNumber)).thenReturn(Optional.of(resultResponse));
        assertThat(resultResponse).isEqualTo(resultService.getResult(orderNumber));
    }

}
