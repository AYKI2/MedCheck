package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/s3")
@CrossOrigin
public class S3API {
    private final S3FileService service;

    @PostMapping("/upload")
    Map<String, String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return service.upload(file);
    }


}
