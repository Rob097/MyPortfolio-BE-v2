package com.myprojects.myportfolio.core.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final JwtTokenValidation jwtTokenValidation;

    public WebSecurityConfiguration(JwtTokenValidation jwtTokenValidation) {
        this.jwtTokenValidation = jwtTokenValidation;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sM -> sM.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(eH -> eH.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(OPTIONS).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/core/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/core/**").permitAll()
                .anyRequest().authenticated());

        http.addFilterBefore(jwtTokenValidation, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
