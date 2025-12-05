package com.example.store_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем защиту CSRF, чтобы работали обычные POST-формы
                .csrf(csrf -> csrf.disable())
                // Разрешаем доступ ко всем URL без авторизации Spring Security
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // Отключаем стандартную форму входа Spring Security
                .formLogin(form -> form.disable())
                // Отключаем базовую авторизацию (всплывающее окно браузера)
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}