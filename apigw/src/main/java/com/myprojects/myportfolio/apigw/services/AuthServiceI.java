package com.myprojects.myportfolio.apigw.services;

import com.myprojects.myportfolio.clients.auth.AuthenticatedUserClaims;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthServiceI {

    Mono<ResponseEntity<Object>> validateToken(String token);

    Mono<AuthenticatedUserClaims> getAuthenticatedUserClaims(String token);

}
