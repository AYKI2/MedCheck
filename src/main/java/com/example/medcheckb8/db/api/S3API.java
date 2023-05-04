package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/s3")
@CrossOrigin
public class S3API {
    private final S3FileService service;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        return service.saveFile(file);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName){
        return service.downloadFile(fileName);
    }

    @DeleteMapping("/{fileName}")
    public String deleteFile(@PathVariable String fileName){
        return service.deleteFile(fileName);
    }

    @GetMapping
    public List<String> getAllFiles(){
        return service.listAllFile();
    }


}
