package com.reservation.api.user.application.dto;

public record IdVerificationDto(
        String id,
        String token
) {
    public boolean equalsToken(String verificationCode) {
        return this.token.equals(verificationCode);
    }
}
