package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.SearchResponse;
import com.example.medcheckb8.db.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class GlobalSearchAPI {

    private final SearchService searchService;

    @GetMapping
    public List<SearchResponse> search(@RequestParam String word) {
        return searchService.search(word);
    }
}
