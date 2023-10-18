package com.myprojects.myportfolio.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"com.myprojects.myportfolio"}, exclude = {UserDetailsServiceAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.myprojects.myportfolio")
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
