package com.reservation.authentication.config.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.reservation.authentication.config.properties.JwtTokenProperties;
import com.reservation.authentication.config.support.dto.TokenGeneratorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtTokenProperties jwtTokenProperties;

    public String generateAccessToken(TokenGeneratorDto tokenGeneratorDto) {
        JWTCreator.Builder jwt = JWT.create()
                .withSubject(tokenGeneratorDto.jwtSubject())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtTokenProperties.accessTokenValiditySeconds()));

        tokenGeneratorDto.claims().forEach(jwt::withClaim);

        return jwt.sign(jwtTokenProperties.jwtSecretAlgorithm());
    }

    public String generateRefreshToken(TokenGeneratorDto tokenGeneratorDto) {
        return JWT.create()
                .withSubject(tokenGeneratorDto.jwtSubject())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtTokenProperties.refreshTokenValiditySeconds()))
                .sign(jwtTokenProperties.jwtSecretAlgorithm());
    }
}
