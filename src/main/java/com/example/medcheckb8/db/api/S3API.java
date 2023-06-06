package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
@CrossOrigin
@Tag(name = "AWS S3", description = "Bucket control API Endpoints")
public class S3API {
    private final S3FileService service;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "The file upload method",
            description = "Using the method, you can upload a new file")
    Map<String, String> upload(@RequestParam MultipartFile file) throws IOException {
        return service.upload(file);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping
    @Operation(
            summary = "The file delete method",
            description = "Using the method, you can delete the file from the link")
    Map<String, String> delete(String link) {
        return service.delete(link);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/download/{link}")
    @Operation(
            summary = "The file download method",
            description = "Using the method, you can download the file from the link")
    public SimpleResponse download(@PathVariable String link) {
        return service.download(link);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @Operation(
            summary = "Get all files",
            description = "Using the method, you can get all files."
    )
    public List<String> listAllFiles(){
        return service.listAllFiles();
    }
}
