package com.turinghealth.turing.health.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dtmkuridm";
    private final String API_KEY = "632863467625747";
    private final String API_SECRET = "7q5h2L4mlOcMWAA5WUR88zhQGUU";

    @Bean
    public Cloudinary cloudinary(){
        HashMap<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);

        return new Cloudinary(config);
    }

}
