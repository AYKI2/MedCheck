package com.example.medcheckb8.db.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3FileService {
    String saveFile(MultipartFile file);

    ResponseEntity<byte[]> downloadFile(String fileName);

    String deleteFile(String fileName);

    List<String> listAllFile();
}
