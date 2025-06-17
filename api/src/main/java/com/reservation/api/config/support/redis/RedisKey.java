package com.reservation.api.config.support.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter
public enum RedisKey {
    /**
     * 토큰
     */
    TOKEN_REFRESH("REFRESH", 1, Duration.ofDays(7)),

    /**
     * 유저 정보
     */
    USER_AUTHORITY("AUTHORITIES", 1, Duration.ofDays(1L)),
    USER_FIND_ID_KEY("FIND_ID", 1, Duration.ofMinutes(5)),
    USER_RESET_PASSWORD_KEY("RESET_PASSWORD", 1, Duration.ofMinutes(5)),
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
