package com.example.backend_sistema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos (login y test)
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/users/test").permitAll()
                        .requestMatchers("/api/users/usuarios").permitAll()

                        // Endpoints de meseros (públicos para pruebas)
                        .requestMatchers("/api/meseros/**").permitAll()

                        // Endpoints de mesas (públicos para pruebas)
                        .requestMatchers("/api/mesas/**").permitAll()

                        // Endpoints de menú (públicos para pruebas)
                        .requestMatchers("/api/menu/**").permitAll()

                        .requestMatchers("/api/platillos/**").permitAll()

                        .requestMatchers("/api/bebidas/**").permitAll()

                        .requestMatchers("/api/postres/**").permitAll()

                        .requestMatchers("/api/ordenes/**").permitAll()

                        .requestMatchers("/api/detalle_orden/**").permitAll()


                        // H2 Console (solo desarrollo)
                        .requestMatchers("/h2-console/**").permitAll()

                        // Resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*", "http://127.0.0.1:*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
