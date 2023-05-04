package com.example.medcheckb8.db.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface S3FileService {

    Map<String, String> upload(MultipartFile file) throws IOException;
    String deleteFile(String fileName);
}
