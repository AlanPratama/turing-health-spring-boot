//package com.turinghealth.turing.health.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final AuthenticationProvider authenticationProvider;
//    private final LogoutHandler logoutHandler;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        return http.csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests()
////                .requestMatchers("/**").permitAll()
////                .and().build();
////                .anyRequest()
////                .authenticated()
////                .and()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .build();
////                .and()
////                .authenticationProvider(authenticationProvider)
//    }
//}
