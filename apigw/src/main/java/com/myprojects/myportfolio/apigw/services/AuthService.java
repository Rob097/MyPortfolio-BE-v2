package com.myprojects.myportfolio.apigw.services;

import com.myprojects.myportfolio.clients.auth.AuthenticatedUserClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class AuthService implements AuthServiceI {

    @Value("${clients.auth.name}")
    private String authClientName;
    @Value("${clients.auth.path}")
    private String authPath;

    private final WebClient.Builder webClientBuilder;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<ResponseEntity<Object>> validateToken(String token) {

        WebClient authServiceClient = webClientBuilder
                .baseUrl("http://" + authClientName + authPath)
                .build();

        return authServiceClient.get()
                .uri("/validate?token=" + token)
                .retrieve()
                .toEntity(Object.class)
                .timeout(Duration.ofSeconds(5))
                .retry(3);

    }

    @Override
    public Mono<AuthenticatedUserClaims> getAuthenticatedUserClaims(String token) {

        WebClient authServiceClient = webClientBuilder
                .baseUrl("http://" + authClientName + authPath)
                .build();

        Mono<ResponseEntity<AuthenticatedUserClaims>> response = authServiceClient.get()
                .uri("/authorities?token=" + token)
                .retrieve()
                .toEntity(AuthenticatedUserClaims.class)
                .timeout(Duration.ofSeconds(5))
                .retry(3);

        return response.mapNotNull(ResponseEntity::getBody);

    }

}
