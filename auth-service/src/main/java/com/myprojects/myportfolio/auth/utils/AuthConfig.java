package com.myprojects.myportfolio.auth.utils;

import lombok.Data;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
@Component
public class AuthConfig {

    public AuthConfig() {
    }

    @Bean
    @LoadBalanced //necessary because of eureka server-client
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
