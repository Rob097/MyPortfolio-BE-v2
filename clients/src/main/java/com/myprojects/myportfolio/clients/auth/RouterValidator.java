package com.myprojects.myportfolio.clients.auth;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class RouterValidator {


    public static final Map<String, List<HttpMethod>> openApiEndpoints = Map.of(
            "/api/auth/**", List.of(HttpMethod.GET, HttpMethod.POST),
            "/api/core/users", List.of(HttpMethod.POST)
    );

    public Predicate<HttpServletRequest> isHttpServletRequestSecured = (request) -> {
        boolean isMatch = false;
        boolean isUri = openApiEndpoints.containsKey(request.getRequestURI());
        if(isUri){
            isMatch = openApiEndpoints.get(request.getRequestURI()).stream()
                    .map(el -> el.name())
                    .filter(el -> el.equals(request.getMethod()))
                    .findAny().isPresent();
        }
        return isMatch;
    };

}
