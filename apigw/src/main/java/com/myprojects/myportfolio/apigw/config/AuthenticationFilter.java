package com.myprojects.myportfolio.apigw.config;

import com.myprojects.myportfolio.apigw.services.AuthServiceI;
import com.myprojects.myportfolio.clients.auth.SecurityConstants;
import com.myprojects.myportfolio.clients.utils.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
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

        org.springframework.cloud.gateway.route.Route route = exchange.getAttribute(org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route != null)
            log.info("Incoming request for " + route.getId());

        boolean isOpenAPI = routerValidator.isHttpServletRequestSecured.test(exchange.getRequest());

        long startTime = System.currentTimeMillis();
        log.info("Authenticating request");

        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            if (!isOpenAPI) {
                throw new UnauthorizedException("Missing Authorization header");
            } else {
                log.info("Request authentication not required");
                return chain.filter(exchange);
            }
        }

        String token = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
        if (token != null && token.startsWith(jwtConfig.getTokenPrefix())) {
            token = token.replace(jwtConfig.getTokenPrefix(), "");
        }

        return authClient.getAuthenticatedUserClaims(token)
                .flatMap(responseEntity -> {
                    log.info("Request authenticated in " + (System.currentTimeMillis() - startTime) + "ms");

                    // Stringify responseEntity as json and set it to the header
                    exchange.getRequest().mutate().headers(httpHeaders ->
                            httpHeaders.add(jwtConfig.getAuthenticatedUserClaimsHeader(), responseEntity.toString())
                    );

                    return chain.filter(exchange);
                })
                .onErrorResume(throwable -> {
                    log.warn("Request authentication failed in " + (System.currentTimeMillis() - startTime) + "ms");
                    ResponseStatusException ex = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are unauthorized to access this resource.");
                    return Mono.error(ex);

                });

    }

}
