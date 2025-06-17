package com.reservation.authentication.config.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reservation.authentication.config.properties.JwtTokenProperties;
import com.reservation.authentication.config.support.dto.TokenGeneratorDto;
import com.reservation.authentication.domain.type.Authority;
import com.reservation.authentication.utils.HttpHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

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

    public boolean verifyToken(Authority authority, String token, String userIdx) {
        try {
            DecodedJWT decodedJWT = JWT.require(jwtTokenProperties.jwtSecretAlgorithm())
                    .build()
                    .verify(token);

            if (authority.isAll()) {
                return true;
            }

            Claim authorityClaim = decodedJWT.getClaim(HttpHeaderUtils.XHeaders.AUTHORITY.name().toLowerCase());
            if (authorityClaim.asString().equalsIgnoreCase(authority.name()) && decodedJWT.getSubject().equals(userIdx)) {
                return true;
            } else {
                throw new JWTVerificationException("Invalid authority");
            }

        } catch (JWTVerificationException ex) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(401), ex.getMessage());
        }
    }
}
