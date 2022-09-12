package com.myprojects.myportfolio.clients.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Component
//@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {

    private final String secretKey = "BvPHGM8C0ia4uOuxxqPD5DTbWC9F9TWvPStp3pb7ARo0oK2mJ3pd3YG4lxA9i8bj6OTbadwezxgeEByY";
    private final String tokenPrefix = "Bearer ";
    private final Integer tokenExpirationAfterDays = 14;

    /* CONSTANTS */
    public static List<String> ALLOWED_ORIGINS = Arrays.asList("http://localhost:8083", "http://localhost:4200", "https://myportfolio-6a771.web.app");
    public static List<String> ALLOW_METHODS = Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELETE");
    public static List<String> ALLOW_HEADERS = Arrays.asList("*");

    public JwtConfig() {
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public String getInternalAuthorizationHeader() {
        return "Internal-Authorization";
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}
