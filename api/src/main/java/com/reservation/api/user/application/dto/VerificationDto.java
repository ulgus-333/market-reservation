package com.reservation.api.user.application.dto;

import lombok.Getter;

@Getter
public abstract class VerificationDto {
    private final String token;

    public VerificationDto(String token) {
        this.token = token;
    }

    public boolean notEqualsToken(String verificationCode) {
        return !this.token.equals(verificationCode);
    }
}
