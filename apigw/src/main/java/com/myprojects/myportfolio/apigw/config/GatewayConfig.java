package com.myprojects.myportfolio.apigw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Value("${clients.routing-prefix}")
    private String routingPrefix;

    @Value("${clients.auth.name}")
    private String authClientName;
    @Value("${clients.auth.path}")
    private String authPath;

    @Value("${clients.core.name}")
    private String coreClientName;
    @Value("${clients.core.path}")
    private String corePath;

    final AuthenticationFilter authenticationFilter;

    public GatewayConfig(@Lazy AuthenticationFilter filter) {
        this.authenticationFilter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("core-service", r -> r.path(corePath + "/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(routingPrefix + coreClientName))

                .route("auth-service", r -> r.path(authPath + "/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(routingPrefix + authClientName))
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClient() {
        return WebClient.builder();
    }

}
