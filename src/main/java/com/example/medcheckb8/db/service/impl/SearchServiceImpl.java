package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.SearchResponse;
import com.example.medcheckb8.db.entities.Doctor;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {
    private final DoctorRepository doctorRepository;
    private static final Logger logger = Logger.getLogger(Doctor.class.getName());

    @Override
    public List<SearchResponse> search(String word) {
        logger.info("Поиск термина: {}" + word);
        List<SearchResponse> results = doctorRepository.globalSearch(word);
        logger.info("Найдено {} результатов для термина: {}" + results.size() + word);
        return results;
    }
}
