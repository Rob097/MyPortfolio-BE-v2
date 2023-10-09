package com.myprojects.myportfolio.clients.auth;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class RouterValidator {


    public static final Map<String, List<HttpMethod>> openApiEndpoints = Map.of(
            "/api/auth/signin", List.of(HttpMethod.POST),
            "/api/auth/signup", List.of(HttpMethod.POST),
            "/api/core/users", List.of(HttpMethod.POST, HttpMethod.GET),
            "/api/core/new", List.of(HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT),
            "/api/core", List.of(HttpMethod.GET)
    );

    public Predicate<HttpServletRequest> isHttpServletRequestSecured = (request) -> {
        boolean isMatch = false;
        String route = null;

        for (String key : openApiEndpoints.keySet()) {
            if (request.getRequestURI().contains(key)) {
                if (route != null && route.length() > key.length()) {
                    continue;
                }
                route = key;
            }
        }

        if (route != null) {
            isMatch = openApiEndpoints.get(route).stream()
                    .map(Enum::name)
                    .anyMatch(el -> el.equals(request.getMethod()));
        }
        return isMatch;
    };

}
