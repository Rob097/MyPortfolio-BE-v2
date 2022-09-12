package com.myprojects.myportfolio.core;

import com.myprojects.myportfolio.clients.auth.JwtTokenVerifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenValidation extends OncePerRequestFilter {

    private JwtTokenVerifier jwtTokenVerifier;

    public JwtTokenValidation(JwtTokenVerifier jwtTokenVerifier) {
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            this.jwtTokenVerifier.validateToken(request);
            filterChain.doFilter(request, response);

        } catch (ResponseStatusException e) {
            response.setStatus(e.getStatus().value());
            response.getWriter().write(e.getMessage());
        }
    }

}
