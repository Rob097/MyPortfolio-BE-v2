package com.myprojects.myportfolio.clients.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplicationUserRole {
    ADMIN(1, "ROLE_ADMIN"),
    BASIC(2, "ROLE_BASIC");

    private final Integer id;
    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
