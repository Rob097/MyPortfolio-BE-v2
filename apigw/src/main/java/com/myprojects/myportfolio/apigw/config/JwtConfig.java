package com.myprojects.myportfolio.apigw.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public String getInternalAuthorizationHeader() {
        return "Internal-Authorization";
    }

}
