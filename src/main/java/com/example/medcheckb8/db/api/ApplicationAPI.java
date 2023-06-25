package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.ApplicationRequest;
import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.dto.response.PaginationResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
@CrossOrigin
public class ApplicationAPI {
    private final ApplicationService service;

    @PostMapping("/add")
    @Operation(summary = "Метод для сохранения запроса.",
            description = "Вы можете применить этот метод для сохранения запроса.")
    public SimpleResponse addApplication(@RequestBody @Valid ApplicationRequest request) {
        return service.addApplication(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getAll")
    @Operation(summary = "Способ получения всех заброшенных заявок.",
            description = "С помощью этого метода администратор может получить все запрошенные заявки с пагинацией. Только для администратора.")
    public PaginationResponse<ApplicationResponse> getAllApplication(@RequestParam(required = false) String word,
                                                                     @RequestParam(required = false, defaultValue = "1") int page,
                                                                     @RequestParam(required = false, defaultValue = "50") int size) {
        return service.getAllApplication(word,page,size);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Способ получения заброшенных заявок по идентификатору.",
            description = "С помощью этого метода администратор может найти заброшенные заявки по идентификатору. Только для администратора.")
    public ApplicationResponse findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Способ удаления заброшенных заявок по идентификатору.",
            description = "С помощью этого метода администратор может удалить заброшенные заявки по идентификатору. Только для администратора.")
    public SimpleResponse deleteById(@RequestBody List<Long> id) {
        return service.deleteByIdApplication(id);
    }
}
