package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.SearchResponse;

import java.util.List;

public interface SearchService {
    List<SearchResponse> search(String word);

}
