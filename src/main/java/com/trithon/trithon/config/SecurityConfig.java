package com.trithon.trithon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain을 사용하여 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API에서 CSRF 보호 비활성화
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/signup", "/auth/login", "/error").permitAll() // 회원가입과 로그인은 인증 없이 접근 가능
                        .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                )
                .formLogin().disable() // 기본 로그인 폼 비활성화 (API 기반 인증 시)
                .httpBasic().disable(); // 기본 HTTP 인증 비활성화

        return http.build();
    }
}
