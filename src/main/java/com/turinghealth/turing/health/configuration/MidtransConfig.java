package com.turinghealth.turing.health.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class MidtransConfig {

    private static final String serverKey = "SB-Mid-server-mu_29ZNsP9StCc78IuGHnUii";

    @Bean
    public static HttpHeaders httpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = serverKey + ":";
        byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodeAuth);

        httpHeaders.set("Authorization", authHeader);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }

}
