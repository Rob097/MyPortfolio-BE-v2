package com.myprojects.myportfolio.core;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfig {

    @Bean
    @LoadBalanced //necessary because of eureka server-client
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
