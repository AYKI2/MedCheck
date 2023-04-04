package com.example.medcheckb8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class MedcheckB8Application {

    public static void main(String[] args) {
        SpringApplication.run(MedcheckB8Application.class, args);
    }

    @GetMapping
    String introduction(){
        return "introduction";
    }

}
