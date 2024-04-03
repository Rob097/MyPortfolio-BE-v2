package com.myprojects.myportfolio.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoreUser {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String customizations = "{}";
    private String token;

}
