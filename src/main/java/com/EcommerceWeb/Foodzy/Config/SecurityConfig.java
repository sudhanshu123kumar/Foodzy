package com.EcommerceWeb.Foodzy.Config;

import com.EcommerceWeb.Foodzy.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration configuration
    ) throws Exception{

        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource sources = new UrlBasedCorsConfigurationSource();

        sources.registerCorsConfiguration("/**", configuration);

        return sources;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception{

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                                .requestMatchers(
                                        "/api/auth/register",
                                        "/api/auth/login"
                                ).permitAll()

                                   .requestMatchers(
                                   "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**"
                                    ).permitAll()
                                // Product & Category view public
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/products/**",
                                        "/api/categories/**"
                                ).permitAll()
                               // image public
                                 .requestMatchers(
                                         "/api/images/**"
                                 ).permitAll()
                               // Admin Product & Category management
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/products/**",
                                        "/api/categories/**"
                                ).hasRole("ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/products/**",
                                        "/api/categories/**"
                                ).hasRole("ADMIN")


                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/products/**",
                                        "/api/categories/**"
                                ).hasRole("ADMIN")

                                 // User own profile
                                .requestMatchers(
                                        "/api/user/me"
                                ).hasRole("USER")

                                 // Admin user management
                                .requestMatchers(
                                        "/api/user/**"
                                ).hasRole("ADMIN")
                                // Admin module
                                .requestMatchers(
                                "/api/admin/**"
                                 ).hasRole("ADMIN")
                                 // User modules
                                .requestMatchers(
                                        "/api/cart/**",
                                        "/api/orders/**",
                                        "/api/profile/**"
                                ).hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                authenticationEntryPoint
                        )
                        .accessDeniedHandler(
                                accessDeniedHandler
                        )
                )
                        .addFilterBefore(
                                jwtAuthenticationFilter,
                                UsernamePasswordAuthenticationFilter.class
                        );


        return http.build();
    }
}
