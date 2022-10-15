package com.myprojects.myportfolio.auth.jwt;

import com.myprojects.myportfolio.clients.auth.JwtTokenVerifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenValidation extends OncePerRequestFilter {

    private JwtTokenVerifier jwtTokenVerifier;

    public JwtTokenValidation(JwtTokenVerifier jwtTokenVerifier) {
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            List<String> authorities = this.jwtTokenVerifier.validateToken(request);
            if(authorities!=null && !authorities.isEmpty()){

                String username = authorities.get(0);
                authorities.remove(0);

                Set<SimpleGrantedAuthority> simpleGrantedRolesAndAuthorities = authorities.stream()
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
