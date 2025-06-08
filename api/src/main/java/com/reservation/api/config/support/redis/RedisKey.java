package com.reservation.api.config.support.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter
public enum RedisKey {
    USER_AUTHORITY("AUTHORITIES", 1, Duration.ofDays(1L)),
    ;

    private static final String KEY_CHAIN = "::";

    private final String baseKey;
    private final int parameterLength;
    private final Duration duration;

    private boolean isSingleKey() {
        return this.parameterLength == 0;
    }

    public String key(String... parameters) {
        if (isSingleKey()) {
            return baseKey;
        }

        if (parameters == null || parameters.length == 0 || parameters.length != parameterLength) {
            throw new RuntimeException("invalid parameters");
        }

        return baseKey + KEY_CHAIN + String.join(KEY_CHAIN, parameters);
    }
}
