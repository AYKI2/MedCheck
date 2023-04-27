package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.dto.response.SearchResponse;
import com.example.medcheckb8.db.repository.DoctorRepository;
import com.example.medcheckb8.db.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final DoctorRepository doctorRepository;

    @Override
    public List<SearchResponse> search(String word) {
        return doctorRepository.globalSearch(word);
    }
}
