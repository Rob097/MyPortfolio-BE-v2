package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dto.EmailMessageDto;
import com.myprojects.myportfolio.core.services.EmailServiceI;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController("EmailController")
@RequestMapping("${core-module-basic-path}" + "/email")
public class EmailController extends SimpleController<EmailMessageDto> {

    private final EmailServiceI emailService;

    public EmailController(EmailServiceI emailService) {
        this.emailService = emailService;
    }

    @PostMapping(path = "/send")
    public ResponseEntity<MessageResources<String>> send(
            @RequestBody EmailMessageDto emailMessageDto
    ) {
        try {
            if (emailMessageDto.getIsHtml() != null && emailMessageDto.getIsHtml()) {
                emailService.sendHtmlEmail(emailMessageDto.getEmail(), emailMessageDto.getSubject(), emailMessageDto.getMessage());
            } else {
                emailService.sendEmail(emailMessageDto.getEmail(), emailMessageDto.getSubject(), emailMessageDto.getMessage());
            }
        } catch (MessagingException e) {
            log.error("Error sending email", e);
            return ResponseEntity.internalServerError().body(new MessageResources<>(null, List.of(new Message(e.getMessage()))));
        }

        return this.buildSuccessResponsesOfGenericType(new ArrayList<>(), Normal.value, new ArrayList<>(), false);
    }
}
