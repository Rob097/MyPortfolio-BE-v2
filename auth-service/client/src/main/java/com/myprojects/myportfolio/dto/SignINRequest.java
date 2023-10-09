package com.myprojects.myportfolio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignINRequest {

    private String username;
    private String password;
    private Boolean rememberMe;

}
