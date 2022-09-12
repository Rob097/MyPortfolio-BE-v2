package com.myprojects.myportfolio.clients.auth;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login"
    );

    public Predicate<HttpServletRequest> isHttpServletRequestSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getRequestURI().contains(uri));

}
