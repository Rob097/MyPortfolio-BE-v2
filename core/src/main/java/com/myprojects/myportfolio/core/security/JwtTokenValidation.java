package com.myprojects.myportfolio.core.security;

import com.google.common.base.Strings;
import com.myprojects.myportfolio.clients.auth.JwtConfig;
import com.myprojects.myportfolio.clients.auth.JwtTokenVerifier;
import com.myprojects.myportfolio.core.CoreConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtTokenValidation extends OncePerRequestFilter {

    private JwtTokenVerifier jwtTokenVerifier;

    private final CoreConfig coreConfig;

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtTokenValidation(JwtTokenVerifier jwtTokenVerifier, CoreConfig coreConfig, SecretKey secretKey, JwtConfig jwtConfig) {
        this.jwtTokenVerifier = jwtTokenVerifier;
        this.coreConfig = coreConfig;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            this.jwtTokenVerifier.validateToken(request);

            String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

            if(!Strings.isNullOrEmpty(authorizationHeader)) {
                String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();

                String username = body.getSubject();

                List<String> authorities = (List<String>) body.get("authorities");
                List<String> roles = (List<String>) body.get("roles");
                List<String> rolesAndAuthorities =  Stream.concat(roles.stream(), authorities.stream()).toList();

                Set<SimpleGrantedAuthority> simpleGrantedRolesAndAuthorities = rolesAndAuthorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m))
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
            response.setStatus(e.getStatus().value());
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        /*for(String pattern : coreConfig.ALLOWED_PATHS){
            if(antPathMatcher.match(pattern, request.getServletPath())){
                return true;
            }
        }*/

        return false;
    }

}
