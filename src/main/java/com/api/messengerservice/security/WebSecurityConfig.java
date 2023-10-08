package com.api.messengerservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123"))
                        .authorities("ADMIN")
                        .build(),
                User.withUsername("employee")
                        .password(passwordEncoder().encode("employee123"))
                        .authorities("EMPLOYEE")
                        .build(),
                User.withUsername("client")
                        .password(passwordEncoder().encode("client123"))
                        .authorities("CLIENT")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationRequest -> authorizationRequest
                        .requestMatchers("/api/v1/client").hasAnyAuthority("CLIENT","ADMIN")
                        .requestMatchers("/api/v1/client/{id}").hasAnyAuthority("CLIENT","ADMIN")
                        .requestMatchers("/api/v1/employee").hasAnyAuthority("EMPLOYEE","ADMIN")
                        .requestMatchers("/api/v1/employee/{id}").hasAnyAuthority("EMPLOYEE","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/shipment").hasAnyAuthority("CLIENT","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/shipment").hasAnyAuthority("EMPLOYEE","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipment").hasAnyAuthority("EMPLOYEE","CLIENT","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipment/list").hasAnyAuthority("EMPLOYEE","ADMIN"))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
