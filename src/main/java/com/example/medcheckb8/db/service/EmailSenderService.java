package com.example.medcheckb8.db.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
