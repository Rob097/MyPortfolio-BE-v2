package com.myprojects.myportfolio.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.myprojects.myportfolio"}, exclude = {ReactiveUserDetailsServiceAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.myprojects.myportfolio"})
public class ApiGWApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGWApplication.class, args);
    }

}
