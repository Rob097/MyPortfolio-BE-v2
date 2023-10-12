package com.myprojects.myportfolio.core.security;

import com.myprojects.myportfolio.clients.auth.JwtTokenVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenValidation extends OncePerRequestFilter {

    private final JwtTokenVerifier jwtTokenVerifier;

    public JwtTokenValidation(JwtTokenVerifier jwtTokenVerifier) {
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            List<String> authorities = this.jwtTokenVerifier.validateToken(request);
            if (authorities != null && !authorities.isEmpty()) {

                String username = authorities.get(0);
                authorities.remove(0);

                Set<SimpleGrantedAuthority> simpleGrantedRolesAndAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedRolesAndAuthorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

            filterChain.doFilter(request, response);

        } catch (ResponseStatusException e) {
            response.setStatus(e.getBody().getStatus());
            response.getWriter().write(Objects.requireNonNull(e.getMessage()));
        }
    }

}
