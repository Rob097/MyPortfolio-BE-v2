package com.myprojects.myportfolio.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.myportfolio.clients.auth.AuthenticatedUserClaims;
import com.myprojects.myportfolio.clients.auth.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenValidation extends OncePerRequestFilter {

    private final SecurityConstants securityConstants;

    public JwtTokenValidation(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {

            // Get the header "AuthenticatedUserClaims" from the request
            String claimsHeader = request.getHeader(securityConstants.getAuthenticatedUserClaimsHeader());

            if (StringUtils.isNotBlank(claimsHeader)) {
                logger.info("AuthenticatedUserClaims header is present");
                AuthenticatedUserClaims authenticatedUserClaims = (new ObjectMapper()).readValue(claimsHeader, AuthenticatedUserClaims.class);

                Set<SimpleGrantedAuthority> simpleGrantedRolesAndAuthorities = authenticatedUserClaims.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        authenticatedUserClaims.getUsername(),
                        null,
                        simpleGrantedRolesAndAuthorities
                );

                logger.info("Authentication set for user: " + authenticatedUserClaims.getUsername());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("AuthenticatedUserClaims header is missing");
            }

            filterChain.doFilter(request, response);

        } catch (ResponseStatusException e) {
            logger.error("ResponseStatusException occurred: {}", e);
            response.setStatus(e.getBody().getStatus());
            response.getWriter().write(Objects.requireNonNull(e.getMessage()));
        } catch (IOException | ServletException e) {
            logger.error("Exception occurred: {}", e);
            throw new RuntimeException(e);
        }
    }

}
