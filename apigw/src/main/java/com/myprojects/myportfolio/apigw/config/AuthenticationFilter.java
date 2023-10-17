package com.myprojects.myportfolio.apigw.config;

import com.myprojects.myportfolio.apigw.services.AuthServiceI;
import com.myprojects.myportfolio.clients.auth.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class AuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;

    private final SecurityConstants jwtConfig;

    private final AuthServiceI authClient;

    public AuthenticationFilter(RouterValidator routerValidator, SecurityConstants jwtConfig, AuthServiceI authClient) {
        this.routerValidator = routerValidator;
        this.jwtConfig = jwtConfig;
        this.authClient = authClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (!routerValidator.isHttpServletRequestSecured.test(exchange.getRequest())) {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing Authorization header");
            }

            String token = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            if (token != null && token.startsWith(jwtConfig.getTokenPrefix())) {
                token = token.replace(jwtConfig.getTokenPrefix(), "");
            }

            return authClient.getAuthenticatedUserClaims(token)
                    .flatMap(responseEntity -> {

                        // Stringify responseEntity as json and set it to the header
                        exchange.getRequest().mutate().headers(httpHeaders ->
                                httpHeaders.add(jwtConfig.getAuthenticatedUserClaimsHeader(), responseEntity.toString())
                        );

                        return chain.filter(exchange);
                    });
        }

        return chain.filter(exchange);
    }

}
