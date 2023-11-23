package com.myprojects.myportfolio.apigw.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component(value = "internalRouterValidator")
public class RouterValidator {


    public static final Map<String, List<HttpMethod>> openApiEndpoints = Map.of(
            "/api/auth/signin", List.of(HttpMethod.POST),
            "/api/auth/signup", List.of(HttpMethod.POST),
            "/api/auth/validate", List.of(HttpMethod.GET),
            "/api/core/users", List.of(HttpMethod.POST, HttpMethod.GET),
            "/api/core/email", List.of(HttpMethod.POST),
            "/api/core", List.of(HttpMethod.GET)
    );

    public Predicate<ServerHttpRequest> isHttpServletRequestSecured = (request) -> {
        boolean isMatch = false;
        String route = null;

        for (String key : openApiEndpoints.keySet()) {
            if (request.getURI().getPath().contains(key)) {
                if (route != null && route.length() > key.length()) {
                    continue;
                }
                route = key;
            }
        }

        if (route != null) {
            isMatch = openApiEndpoints.get(route).stream()
                    .map(HttpMethod::name)
                    .anyMatch(el -> el.equals(request.getMethod().name()));
        }
        return isMatch;
    };

}
