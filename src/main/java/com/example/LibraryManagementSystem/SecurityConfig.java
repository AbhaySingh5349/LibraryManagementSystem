package com.example.LibraryManagementSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// whenever we want to create our own beans, we need '@Configuration' annotation
// http://localhost:8080/spring-security

@Configuration
public class SecurityConfig {
    // to specify which API can be accessed different authorities (authorization for routes)
    // API should have at least 1 of authorities assigned to users
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/transaction/issue").hasAuthority("STUDENT")
                        .requestMatchers("/transaction/return").hasAuthority("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(withDefaults()) // we need a login form for accessing authorized routes (browser)
                .httpBasic(withDefaults()) // for postman, we need to provide "Basic Auth"
                .csrf(csrf -> csrf.disable()); // to enable post requests through POSTMAN
        return http.build();
    }

    // password encoding
    @Bean
    public PasswordEncoder getEncoder(){
        return NoOpPasswordEncoder.getInstance(); // no encryption is done for user & password, plain text comparison
    }
}
