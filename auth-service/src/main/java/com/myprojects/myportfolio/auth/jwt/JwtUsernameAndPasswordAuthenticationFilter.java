package com.myprojects.myportfolio.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.myportfolio.auth.auth.AuthenticationUser;
import com.myprojects.myportfolio.auth.auth.DBUser;
import com.myprojects.myportfolio.auth.dto.SignINRequest;
import com.myprojects.myportfolio.clients.auth.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtConfig jwtConfig,
                                                      SecretKey secretKey) {
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth/signin","POST"));
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            SignINRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), SignINRequest.class);

            this.saveRememberMeChoiceInSession(request, authenticationRequest);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Integer expirationAfterDays = jwtConfig.getTokenExpirationAfterDays();
        if(this.readRememberMeChoiceFromSession(request)){
            expirationAfterDays = jwtConfig.getTokenExpirationAfterDaysRememberMe();
        }

        AuthenticationUser applicationUser = (AuthenticationUser) authResult.getPrincipal();
        DBUser dbUser = applicationUser.getDBUser();

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("firstName", dbUser.getFirstName())
                .claim("lastName", dbUser.getLastName())
                .claim("roles", applicationUser.getRolesName())
                .claim("authorities", applicationUser.getAuthoritiesName())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(expirationAfterDays)))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        log.error(failed.getMessage());
    }

    private void saveRememberMeChoiceInSession(HttpServletRequest request, SignINRequest authenticationRequest){
        HttpSession session = request.getSession();
        if (session != null || getAllowSessionCreation()) {
            session.setAttribute(jwtConfig.getRememberMeSessionAttribute(), authenticationRequest.getRememberMe());
        }
    }

    private boolean readRememberMeChoiceFromSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null) {
            Boolean isRememberMe = (Boolean) session.getAttribute(jwtConfig.getRememberMeSessionAttribute());
            if(isRememberMe!=null){
                return isRememberMe;
            }
        }
        return false;
    }

}
