package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.response.SimpleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface S3FileService {

    Map<String, String> upload(MultipartFile file) throws IOException;

    Map<String, String> delete(String fileLink);
    SimpleResponse download(String fileLink);
    List<String> listAllFiles();
}
