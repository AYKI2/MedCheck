package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.exceptions.DownloadFailedException;
import com.example.medcheckb8.db.service.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class S3FileServiceImpl implements S3FileService {
    @Value("${AWS_BUCKET_PATH}")
    private String BUCKET_PATH;
    @Value("${AWS_BUCKET_NAME}")
    private String BUCKET_NAME;
    private final S3Client s3;
    private static final Logger logger = Logger.getLogger(S3FileServiceImpl.class.getName());


    @Autowired
    public S3FileServiceImpl(S3Client s3) {
        this.s3 = s3;
    }

    @Override
    public Map<String, String> upload(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .contentType("jpeg")
                .contentType("png")
                .contentType("ogg")
                .contentType("mp3")
                .contentType("mpeg")
                .contentType("ogg")
                .contentType("m4a")
                .contentType("oga")
                .contentType("pdf")
                .contentLength(file.getSize())
                .key(key)
                .build();
        s3.putObject(put, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        logger.log(Level.INFO, "File uploaded: Key={0}",
                new Object[]{key});
        return Map.of(
                "link", BUCKET_PATH + key);
    }

    @Override
    public Map<String, String> delete(String fileLink) {
        log.info("Deleting file...");

        try {
            String key = fileLink.replace(BUCKET_PATH,"");
            log.warn("Deleting object: {}", key);

            s3.deleteObject(dor -> dor.bucket(BUCKET_NAME).key(key).build());

        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return Map.of(
                "message", fileLink + " has been deleted");
    }

    @Override
    public byte[] download(String fileLink) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileLink)
                    .build();

            ResponseBytes<GetObjectResponse> responseBytes = s3.getObjectAsBytes(getObjectRequest);

            return responseBytes.asByteArray();
        } catch (S3Exception e) {
            throw new DownloadFailedException("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    @Override
    public List<String> listAllFiles(){
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(BUCKET_NAME)
                .build();
        ListObjectsV2Response listObjectsV2Result = s3.listObjectsV2(listObjectsRequest);
        return listObjectsV2Result.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }
}
