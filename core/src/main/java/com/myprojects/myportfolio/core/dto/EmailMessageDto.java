package com.myprojects.myportfolio.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDto {

    private Integer userId;
    private String to;
    private String language = "it";
    private String name;
    private String email;
    private String subject;
    private String message;
    private Boolean isHtml;
    private String recaptchaToken;

}
