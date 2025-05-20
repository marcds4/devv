package com.example.devFlow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/", "/images/**", "/css/**", "/js/**", "/webjars/**").permitAll()
                .requestMatchers("/login","/create","/create_profile", "/create_profile_dev", "/login_success","/success", "/login_info", "/register", "/signup", "/signup_info", "/check-email","/check-username","/check-email-login", "/check-password-login").permitAll()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                    .loginPage("/login").permitAll()
                )
            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }
}


