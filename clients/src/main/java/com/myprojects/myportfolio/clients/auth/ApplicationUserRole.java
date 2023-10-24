package com.myprojects.myportfolio.clients.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for user roles
 * IMPORTANT:
 * The name of roles MUST start with "ROLE_" prefix
 * If not, they don't work in @PreAuthorize conditions
 */
@Getter
@AllArgsConstructor
public enum ApplicationUserRole {
    ADMIN(1, "ROLE_ADMIN"),
    BASIC(2, "ROLE_BASIC"),
    SYS_ADMIN(3, "ROLE_SYS_ADMIN"),
    ;

    private final Integer id;
    private final String name;

}
