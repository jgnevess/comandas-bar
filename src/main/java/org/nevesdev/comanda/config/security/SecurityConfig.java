package org.nevesdev.comanda.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/swagger-ui.html",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                                ).permitAll()
                                .requestMatchers(HttpMethod.HEAD).permitAll()
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()//hasRole("SUPER")
                                .requestMatchers(HttpMethod.GET, "/api/auth/valid").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/product/update/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/product/remove/**").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/product/fast-active/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/product/add/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/product/inactive").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/sale").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/sale/totalBetween*").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/sale/{id}").authenticated()
                                .anyRequest().authenticated()).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
