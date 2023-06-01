package com.example.medcheckb8.application;

import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.entities.Application;
import com.example.medcheckb8.db.repository.ApplicationRepository;
import com.example.medcheckb8.db.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ApplicationServiceTest {
    @Mock
    private ApplicationRepository repository;
    @InjectMocks
    private ApplicationServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplicationSave() {
        ApplicationRequest request = ApplicationRequest
                .builder()
                .name("Test Name")
                .phoneNumber("+996111222333")
                .build();
        Mockito.when(repository.save(Mockito.any(Application.class))).thenReturn(new Application());
        SimpleResponse response = service.addApplication(request);
        Mockito.verify(repository, Mockito.times(2)).save(Mockito.any(Application.class));
        Assertions.assertEquals(HttpStatus.OK, response.status());
        Assertions.assertTrue(response.message().contains("Successfully"));
    }

    @Test
    void testDeleteByIdApplication() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = Arrays.asList(id1, id2);

        Mockito.when(repository.findById(id1)).thenReturn(Optional.of(new Application()));
        Mockito.when(repository.findById(id2)).thenReturn(Optional.of(new Application()));

        SimpleResponse response = service.deleteByIdApplication(ids);

        Mockito.verify(repository, Mockito.times(2)).findById(Mockito.anyLong());
        Mockito.verify(repository, Mockito.times(1)).deleteApplications(ids);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        Assertions.assertFalse(response.message().contains("successfully deleted"));
    }


}
