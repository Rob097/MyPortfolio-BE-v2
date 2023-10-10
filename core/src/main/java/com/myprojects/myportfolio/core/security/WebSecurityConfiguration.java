package com.myprojects.myportfolio.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final JwtTokenValidation jwtTokenValidation;

    public WebSecurityConfiguration(JwtTokenValidation jwtTokenValidation) {
        this.jwtTokenValidation = jwtTokenValidation;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/api/core/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/core/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/core/new/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/core/new/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenValidation, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
