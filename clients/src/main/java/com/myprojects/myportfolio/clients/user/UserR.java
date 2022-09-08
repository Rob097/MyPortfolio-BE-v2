package com.myprojects.myportfolio.clients.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserR {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

}
