package com.myprojects.myportfolio.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecaptchaVerificationResponse {

    private boolean success;
    private String challenge_ts;
    private String hostname;
    private String[] error_codes;

}
