package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.entities.Application;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.service.ApplicationService;
import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.repository.ApplicationRepository;
import com.google.api.gax.rpc.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    @Override
    public SimpleResponse addApplication(ApplicationRequest request) {
        Application application = new Application();
        application.setName(request.name());
        application.setDate(LocalDate.now());
        application.setPhoneNumber(request.phoneNumber());
        application.setProcessed(false);
        repository.save(application);
        try {
            repository.save(application);
            String successMessage = String.format("Successfully %s saved!", request.name());
            logger.info(successMessage);
            return new SimpleResponse(HttpStatus.OK, successMessage);
        } catch (Exception e) {
            String errorMessage = "Error while saving application: " + e.getMessage();
            logger.severe(errorMessage);
            return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong.").build();
        }    }

    @Override
    public List<ApplicationResponse> getAllApplication(String word) {
        if (word == null) {
            logger.info("Retrieved all applications: " + repository.getAllApplication().size());
            return repository.getAllApplication();
        }
        logger.info("Search results for '" + word + "': " + repository.globalSearch(word).size());
        return repository.globalSearch(word);
    }

    @Override
    public SimpleResponse deleteByIdApplication(List<Long> id) {
        for (Long aLong : id) {
            if (repository.findById(aLong).isEmpty()){
                logger.warning("Application not found with ID: " + id);
                throw new NotFountException("Application not found with ID: " + id);

            }
        }
        repository.deleteApplications(id);
        logger.info(String.format("Application with ID: %s successfully deleted!", id));
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Application with ID: %s successfully delete !", id)).build();
    }

    @Override
    public ApplicationResponse findById(Long id) {
        logger.info("Finding application by ID: {}" + id);
        return repository.findByIdApplication(id).orElseThrow(() -> new NotFountException(String.format("Application with ID : %s not found!", id)));
    }

}
