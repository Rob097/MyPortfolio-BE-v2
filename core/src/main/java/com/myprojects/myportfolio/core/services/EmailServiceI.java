package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dto.EmailMessageDto;
import jakarta.mail.MessagingException;

public interface EmailServiceI {

    void sendEmail(String to, EmailMessageDto emailMessageDto);

    void sendHtmlEmail(String to, EmailMessageDto emailMessageDto) throws MessagingException;

}
