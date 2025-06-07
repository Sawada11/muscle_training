package com.example.muscle_api.config;

import com.example.muscle_api.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll() // ← /auth/login を含んでいる必要あり
            .anyRequest().authenticated()
        )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // JWTの場合はstateless

        return http.build();
    }
}
