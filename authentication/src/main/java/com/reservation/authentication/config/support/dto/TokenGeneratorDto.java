package com.reservation.authentication.config.support.dto;

import com.reservation.authentication.domain.type.Authority;

import java.util.HashMap;
import java.util.Map;

public record TokenGeneratorDto(
        String userIdx,
        String marketIdx,
        Authority authority
) {
    public static TokenGeneratorDto appUser(Long userIdx) {
        return new TokenGeneratorDto(String.valueOf(userIdx), null, Authority.USER);
    }

    public String jwtSubject() {
        return this.userIdx;
    }

    public Map<String, String> claims() {
        Map<String, String> claims = new HashMap<>();

        claims.put("authority", authority.name());
        if (authority.isAdmin()) {
            claims.put("marketIdx", marketIdx);
        }

        return claims;
    }
}
