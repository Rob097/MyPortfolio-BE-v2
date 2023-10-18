package com.myprojects.myportfolio.auth.utils;

import com.myprojects.myportfolio.clients.auth.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This component is used to intercept the request between microservices via feignClients.
 * We need the method apply to intercept the authentication headers from the caller microservice and pass to the receiver microservice.
 * Sadly, FeignClients doesn't do it for us, so we need to explicit this behaviour.
 * */
@Component
public class FeignInterceptor implements RequestInterceptor {

    private final SecurityConstants securityConstants;

    public FeignInterceptor(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        String authenticatedUser = requestAttributes.getRequest().getHeader(securityConstants.getAuthenticatedUserClaimsHeader());
        if (StringUtils.isNotBlank(authenticatedUser))
            template.header(securityConstants.getAuthenticatedUserClaimsHeader(), authenticatedUser);
    }

}
