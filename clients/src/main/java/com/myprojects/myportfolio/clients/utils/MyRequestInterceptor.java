package com.myprojects.myportfolio.clients.utils;

import com.myprojects.myportfolio.clients.auth.JwtConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This component is used to intercept the request between microservices via feignClients.
 * We need the method apply to intercept the authentication headers from the caller microservice and pass to the receiver microservice.
 * Sadly, FeignClients doesn't do it for us, so we need to explicit this behaviour.
 * */
@Component
public class MyRequestInterceptor implements RequestInterceptor {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authorization = requestAttributes.getRequest().getHeader(jwtConfig.getAuthorizationHeader());
        String internalAuthorization = requestAttributes.getRequest().getHeader(jwtConfig.getInternalAuthorizationHeader());
        if(null != authorization) {
            template.header(jwtConfig.getAuthorizationHeader(), authorization);
            template.header(jwtConfig.getInternalAuthorizationHeader(), internalAuthorization);
        }
    }

}