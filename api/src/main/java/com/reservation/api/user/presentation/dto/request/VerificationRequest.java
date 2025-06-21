package com.reservation.api.user.presentation.dto.request;

import com.reservation.common.dto.RedisKey;
import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(
        @NotBlank String uuid,
        @NotBlank String verificationCode
) {
    public String redisKey(RedisKey redisKey) {
        return redisKey.key(this.uuid);
    }
}
