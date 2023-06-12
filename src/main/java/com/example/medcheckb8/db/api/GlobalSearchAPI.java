package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.SearchResponse;
import com.example.medcheckb8.db.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
@CrossOrigin
@Tag(name = "GlobalSearch", description = "Expert Search API Endpoints")
public class GlobalSearchAPI {

    private final SearchService searchService;

    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @GetMapping
    @Operation(summary = "Метод глобального поиска",
                    description = "С помощью этого метода вы можете найти врача по имени и фамилии, " +
                            "а также найти отделение.")
    public List<SearchResponse> search(@RequestParam(required = false) String word) {
        return searchService.search(word);
    }
}
