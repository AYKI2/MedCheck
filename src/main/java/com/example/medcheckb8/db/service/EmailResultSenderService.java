package com.example.medcheckb8.db.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailResultSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
