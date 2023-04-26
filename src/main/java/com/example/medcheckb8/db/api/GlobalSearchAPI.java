package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.SearchResponse;
import com.example.medcheckb8.db.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class GlobalSearchAPI {

    private final SearchService searchService;

    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping
    public List<SearchResponse> search(@RequestParam String word) {
        return searchService.search(word);
    }
}
