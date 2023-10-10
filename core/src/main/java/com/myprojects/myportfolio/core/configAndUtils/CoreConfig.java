package com.myprojects.myportfolio.core.configAndUtils;

import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@Data
@Component
public class CoreConfig {

    public CoreConfig(){}

    @Bean
    @LoadBalanced //necessary because of eureka server-client
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * I use this method to save some packages paths in order to use variables into annotations
     */
    @Component
    public static class DefaultMethodSecurityExpressionHandler extends org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler {
        @Override
        public StandardEvaluationContext createEvaluationContextInternal(final Authentication auth, final MethodInvocation mi) {
            StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(auth, mi);
            ((StandardTypeLocator) standardEvaluationContext.getTypeLocator()).registerImport("com.myprojects.myportfolio.clients.auth");
            return standardEvaluationContext;
        }
    }

}
