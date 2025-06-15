package com.reservation.authentication.config.properties;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class JwtTokenProperties {
    @Value("${jwt.algorithm.secret}")
    private String secret;

    @Value("${jwt.token.validity.access}")
    private Duration accessTokenValidity;

    @Value("${jwt.token.validity.refresh}")
    private Duration refreshTokenValidity;

    public Algorithm jwtSecretAlgorithm() {
        return Algorithm.HMAC256(this.secret);
    }

    public long accessTokenValiditySeconds() {
        return this.accessTokenValidity.toMillis();
    }

    public long refreshTokenValiditySeconds() {
        return this.refreshTokenValidity.toMillis();
    }
}
