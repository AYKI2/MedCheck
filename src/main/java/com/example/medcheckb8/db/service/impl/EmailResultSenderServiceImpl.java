package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.service.EmailResultSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailResultSenderServiceImpl implements EmailResultSenderService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("medcheck.service@gmail.com");
            helper.setTo(toEmail);
            helper.setText(body, true);
            helper.setSubject(subject);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new NotFountException("Email send failed!");
        } catch (NullPointerException e) {
            throw new NotFountException("Not found and returned null!");
        }
    }
}
