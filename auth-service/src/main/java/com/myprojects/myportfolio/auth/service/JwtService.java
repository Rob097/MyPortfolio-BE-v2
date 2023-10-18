package com.myprojects.myportfolio.auth.service;

import com.myprojects.myportfolio.clients.auth.AuthenticatedUserClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import com.myprojects.myportfolio.auth.security.JwtConfig;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class JwtService implements JwtServiceI {

    private final JwtConfig jwtConfig;

    private final SecretKey secretKey;

    public JwtService(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public void validateToken(String token) {
        if (jwtConfig.isInvalid(token)) {
            throw new IllegalStateException("Token is invalid");
        }
    }

    @Override
    public AuthenticatedUserClaims getAuthorities(String token) {

        log.info("Getting authorities from token");

        this.validateToken(token);

        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims((token));

        Claims body = claimsJws.getPayload();

        String username = body.getSubject();
        List<String> authorities = (List<String>) body.get("authorities");
        List<String> roles = (List<String>) body.get("roles");
        List<String> result = new ArrayList<>(Stream.concat(roles.stream(), authorities.stream()).toList());

        return new AuthenticatedUserClaims(username, true, result);

    }

}
