package com.myprojects.myportfolio.apigw.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    private final JwtConfig jwtConfig;

    public AuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        request.getHeaders();
        String authorizationHeader = request.getHeaders().getFirst(jwtConfig.getAuthorizationHeader());

        // Set internal authorization header in order to make sure that every request pass through the api gateway (load balancer)
        exchange.getRequest().mutate()
                .header(jwtConfig.getInternalAuthorizationHeader(), authorizationHeader)
                .build();

        return chain.filter(exchange);

    }
}