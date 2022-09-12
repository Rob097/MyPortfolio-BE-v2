package com.myprojects.myportfolio.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.myprojects.myportfolio"})
public class ApiGWApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGWApplication.class, args);
    }

}
