package com.myprojects.myportfolio.clients.auth;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class SecurityConstants {

    /* CONSTANTS */
    public static List<String> ALLOWED_ORIGINS = List.of("http://localhost:8083", "http://localhost:4200", "https://myportfolio-6a771.web.app");
    public static List<String> ALLOW_METHODS = List.of("OPTIONS", "GET", "POST", "PUT", "DELETE");
    public static List<String> ALLOW_HEADERS = List.of("*");

    private final String tokenPrefix = "Bearer ";
    private final String rememberMeSessionAttribute = "spring_security_remember_me";
    private final String authenticatedUserClaimsHeader = "AuthenticatedUserClaims";

    public SecurityConstants() {
    }

}
