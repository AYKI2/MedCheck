package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    private static final Logger logger = Logger.getLogger(EmailSenderService.class.getName());

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
            logger.log(Level.INFO, "Отправлено письмо на адрес: {0}", toEmail);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Не удалось отправить электронное письмо", e);
            throw new NotFountException("Не удалось отправить письмо!");
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Возникло исключение NullPointerException", e);
            throw new NotFountException("Не найдено и вернуто значение null!");
        }
    }
}
