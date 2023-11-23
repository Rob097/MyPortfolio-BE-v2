package com.myprojects.myportfolio.core.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EmailService implements EmailServiceI {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        this.sendEmail(List.of(to), subject, body);
    }

    private void sendEmail(List<String> to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        log.info("Email sent to: {}", to);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(fromAddress);
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");

        mailSender.send(message);

        log.info("Email sent to: {}", to);
    }
}