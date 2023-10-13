package com.myprojects.myportfolio.clients.utils;

import com.myprojects.myportfolio.clients.auth.JwtConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.common.util.StringUtils;
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

    private final JwtConfig jwtConfig;

    public MyRequestInterceptor(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        String authorization = requestAttributes.getRequest().getHeader(jwtConfig.getAuthorizationHeader());
        String internalAuthorization = requestAttributes.getRequest().getHeader(jwtConfig.getInternalAuthorizationHeader());

        if (StringUtils.isNotBlank(authorization))
            template.header(jwtConfig.getAuthorizationHeader(), authorization);

        if (StringUtils.isNotBlank(internalAuthorization))
            template.header(jwtConfig.getInternalAuthorizationHeader(), internalAuthorization);

    }

}