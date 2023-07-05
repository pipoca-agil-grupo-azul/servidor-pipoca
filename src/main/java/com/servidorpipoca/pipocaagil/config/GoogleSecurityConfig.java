package com.servidorpipoca.pipocaagil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class GoogleSecurityConfig {
    @Bean
    public SecurityFilterChain googleFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authorizedConfig) -> authorizedConfig
                        .requestMatchers("/public").permitAll()
                        .anyRequest().authenticated()
                ).oauth2Login(Customizer.withDefaults()).build();
    }
}
