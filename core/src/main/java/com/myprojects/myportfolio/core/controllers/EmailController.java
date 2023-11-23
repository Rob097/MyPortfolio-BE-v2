package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.EmailMessageDto;
import com.myprojects.myportfolio.core.services.EmailServiceI;
import com.myprojects.myportfolio.core.services.UserServiceI;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController("EmailController")
@RequestMapping("${core-module-basic-path}" + "/email")
public class EmailController extends SimpleController<EmailMessageDto> {

    private final UserServiceI userService;

    private final EmailServiceI emailService;

    public EmailController(UserServiceI userService, EmailServiceI emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<MessageResource<Boolean>> validate(
            @RequestParam String token
    ) {
        return ResponseEntity.ok(new MessageResource<>(emailService.verifyRecaptcha(token), List.of()));
    }

    @PostMapping(path = "/send")
    public ResponseEntity<MessageResources<String>> send(
            @RequestBody EmailMessageDto emailMessageDto
    ) {

        if (!emailService.verifyRecaptcha(emailMessageDto.getRecaptchaToken())) {
            return ResponseEntity.badRequest().body(new MessageResources<>(null, List.of(new Message("Invalid recaptcha"))));
        }

        String to = emailMessageDto.getTo();

        if (StringUtils.isBlank(to)) {
            User user = userService.findById(emailMessageDto.getUserId());
            if (user == null || user.getEmail() == null) {
                throw new RuntimeException("User not found");
            }
            to = user.getEmail();
        }

        try {
            if (emailMessageDto.getIsHtml() != null && emailMessageDto.getIsHtml()) {
                emailService.sendHtmlEmail(to, emailMessageDto);
            } else {
                emailService.sendEmail(to, emailMessageDto);
            }
        } catch (MessagingException e) {
            log.error("Error sending email", e);
            return ResponseEntity.internalServerError().body(new MessageResources<>(null, List.of(new Message(e.getMessage()))));
        }

        return this.buildSuccessResponsesOfGenericType(new ArrayList<>(), Normal.value, new ArrayList<>(), false);
    }
}
