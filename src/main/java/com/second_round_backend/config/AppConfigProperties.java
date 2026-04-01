package com.second_round_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "")
@Data
public class AppConfigProperties {

    private String jwtSecret;
    private String stripeSecretKey;

    @PostConstruct
    public void validate() {

        if (jwtSecret == null || jwtSecret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET is missing! Set environment variable.");
        }

        if (stripeSecretKey == null || stripeSecretKey.isBlank()) {
            throw new IllegalStateException("STRIPE_SECRET_KEY is missing! Set environment variable.");
        }
    }
}