package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.dto.response.PaginationResponse;
import com.example.medcheckb8.db.entities.Application;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.service.ApplicationService;
import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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
            String successMessage = String.format("Успешно сохранено: %s!", request.name());
            logger.info(successMessage);
            return new SimpleResponse(HttpStatus.OK, successMessage);
        } catch (Exception e) {
            String errorMessage = "Ошибка при сохранении заявки: " + e.getMessage();
            logger.severe(errorMessage);
            return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Произошла ошибка.").build();
        }
    }

    @Override
    public PaginationResponse<ApplicationResponse> getAllApplication(String word, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        logger.info("Результаты поиска для '" + word + "': " + repository.globalSearch(word,pageable).getTotalElements());
        Page<ApplicationResponse> applicationPage = repository.globalSearch(word,pageable);
        PaginationResponse<ApplicationResponse> response = new PaginationResponse<>();
        response.setResponses(applicationPage.getContent());
        response.setCurrentPage(pageable.getPageNumber() + 1);
        response.setPageSize(applicationPage.getSize());
        return response;
    }

    @Override
    public SimpleResponse deleteByIdApplication(List<Long> id) {
        for (Long aLong : id) {
            if (repository.findById(aLong).isEmpty()) {
                logger.warning("Заявка не найдена с ID:  " + id);
                throw new NotFountException("Заявка не найдена с ID: " + id);

            }
        }
        repository.deleteApplications(id);
        logger.info(String.format("Заявка с ID: %s успешно удалена!", id));
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Заявка с ID: %s успешно удалена!", id)).build();
    }

    @Override
    public ApplicationResponse findById(Long id) {
        logger.info("Поиск заявки по ID: {}" + id);
        return repository.findByIdApplication(id).orElseThrow(() -> new NotFountException(String.format("Заявка с ID: %s не найдена!", id)));
    }
}
