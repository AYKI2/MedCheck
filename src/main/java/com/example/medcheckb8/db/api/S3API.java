package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Метод загрузки файла",
            description = "С помощью этого метода вы можете загрузить новый файл.")
    Map<String, String> upload(@RequestParam MultipartFile file) throws IOException {
        return service.upload(file);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    @Operation(
            summary = "Метод удаления файла",
            description = "С помощью этого метода вы можете удалить файл по ссылке.")
    Map<String, String> delete(String link) {
        return service.delete(link);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/download/{link}")
    @Operation(
            summary = "Метод загрузки файла",
            description = "С помощью этого метода вы можете загрузить файл по ссылке.")
    public ResponseEntity<byte[]> download(@PathVariable String link) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(link).build());

        return new ResponseEntity<>(service.download(link), headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @Operation(
            summary = "Метод получения всех файлов",
            description = "С помощью этого метода вы можете получить все файлы."
    )
    public List<String> listAllFiles(){
        return service.listAllFiles();
    }
}
