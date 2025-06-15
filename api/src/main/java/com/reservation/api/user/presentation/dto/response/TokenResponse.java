package com.reservation.api.user.presentation.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
