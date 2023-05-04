package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.service.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;


@Service
public class S3FileServiceImpl implements S3FileService {

    @Value("${AWS_BUCKET_PATH}")
    private String BUCKET_PATH;
    private final S3Client s3;

    @Autowired
    public S3FileServiceImpl(S3Client s3) {
        this.s3 = s3;
    }

    @Override
    public Map<String, String> upload(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket("medcheckbucket")
                .contentType("jpeg")
                .contentType("png")
                .contentType("ogg")
                .contentType("mp3")
                        .contentType("mpeg")
                        .contentType("ogg")
                        .contentType("m4a")
                        .contentType("oga")
                        .contentLength(file.getSize())
                        .key(key)
                        .build();
        s3.putObject(put, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return Map.of(
                "link", BUCKET_PATH + key);
    }

    @Override
    public String deleteFile(String fileName) {
        return null;
    }
}
