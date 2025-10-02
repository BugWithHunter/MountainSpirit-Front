package com.bughunters.mountainspirit.gateway.config;

import com.bughunters.mountainspirit.gateway.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class GatewaySecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public GatewaySecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // 필터 순서 지정 대신 addFilterAt() 사용 가능
        return http.csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/user/**").hasRole("USER")
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .pathMatchers("/report/**").hasAnyRole("USER", "ADMIN")
                        .anyExchange().authenticated()
                )
                .build();
    }
}
