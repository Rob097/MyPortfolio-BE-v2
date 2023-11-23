package com.myprojects.myportfolio.core.services;

import jakarta.mail.MessagingException;

public interface EmailServiceI {

    void sendEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String body) throws MessagingException;

}
