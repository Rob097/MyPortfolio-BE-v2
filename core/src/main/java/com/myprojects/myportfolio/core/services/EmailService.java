package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dto.EmailMessageDto;
import com.myprojects.myportfolio.core.dto.RecaptchaVerificationResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EmailService implements EmailServiceI {

    private final JavaMailSender mailSender;

    @Value("${google.recaptcha.url}")
    private String recaptchaUrl;

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean verifyRecaptcha(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String fullUri = recaptchaUrl + "?secret=" + recaptchaSecret + "&response=" + token;

        ResponseEntity<RecaptchaVerificationResponse> responseEntity =
                restTemplate.postForEntity(fullUri, null, RecaptchaVerificationResponse.class);

        RecaptchaVerificationResponse responseBody = responseEntity.getBody();
        return responseBody != null && responseBody.isSuccess();
    }

    @Override
    public void sendEmail(String to, EmailMessageDto emailMessageDto) {
        this.sendEmail(List.of(to), emailMessageDto);
    }

    private void sendEmail(List<String> to, EmailMessageDto emailMessageDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to.toArray(new String[0]));
        message.setSubject(emailMessageDto.getSubject());

        String content;
        if (emailMessageDto.getLanguage().equals("it")) {
            content = "Messaggio ricevuto da: \n\n" +
                    "Nome: " + emailMessageDto.getName() + "\n\n" +
                    "Email: " + emailMessageDto.getEmail() + "\n\n" +
                    "Messaggio: " + emailMessageDto.getMessage();
        } else {
            content = "Message received from: \n\n" +
                    "Name: " + emailMessageDto.getName() + "\n\n" +
                    "Email: " + emailMessageDto.getEmail() + "\n\n" +
                    "Message: " + emailMessageDto.getMessage();
        }

        message.setText(content);
        mailSender.send(message);

        log.info("Email sent to: {}", to);
    }

    @Override
    public void sendHtmlEmail(String to, EmailMessageDto emailMessageDto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(fromAddress);
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(emailMessageDto.getSubject());

        String body;
        if (emailMessageDto.getLanguage().equals("IT")) {
            body = "<h1>Messaggio ricevuto da: </h1>" +
                    "<p>Nome: " + emailMessageDto.getName() + "</p>" +
                    "<p>Email: " + emailMessageDto.getEmail() + "</p>" +
                    "<p>Messaggio: " + emailMessageDto.getMessage() + "</p>";
        } else {
            body = "<h1>Message received from: </h1>" +
                    "<p>Name: " + emailMessageDto.getName() + "</p>" +
                    "<p>Email: " + emailMessageDto.getEmail() + "</p>" +
                    "<p>Message: " + emailMessageDto.getMessage() + "</p>";
        }

        message.setContent(body, "text/html; charset=utf-8");
        mailSender.send(message);

        log.info("Email sent to: {}", to);
    }
}