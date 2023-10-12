package com.myprojects.myportfolio.clients.utils;

import com.myprojects.myportfolio.clients.auth.JwtConfig;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * This component is used to intercept the request between microservices via feignClients.
 * We need the method apply to intercept the authentication headers from the caller microservice and pass to the receiver microservice.
 * Sadly, FeignClients doesn't do it for us, so we need to explicit this behaviour.
 * */
@Component
public class MyRequestInterceptor implements RequestInterceptor {

    private final JwtConfig jwtConfig;

    public MyRequestInterceptor(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public void apply(RequestTemplate template) {
        Request request = template.request();
        String authorization = request.headers().get(jwtConfig.getAuthorizationHeader()).iterator().next();
        String internalAuthorization = request.headers().get(jwtConfig.getInternalAuthorizationHeader()).iterator().next();
        if (null != authorization) {
            template.header(jwtConfig.getAuthorizationHeader(), authorization);
            template.header(jwtConfig.getInternalAuthorizationHeader(), internalAuthorization);
        }
    }

}