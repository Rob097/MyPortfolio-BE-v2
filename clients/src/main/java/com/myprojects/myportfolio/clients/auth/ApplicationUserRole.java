package com.myprojects.myportfolio.clients.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationUserRole {
    ADMIN(1, "ROLE_ADMIN"),
    BASIC(2, "ROLE_BASIC"),
    SYS_ADMIN(3, "SYS_ADMIN"),
    ;

    private final Integer id;
    private final String name;

}
