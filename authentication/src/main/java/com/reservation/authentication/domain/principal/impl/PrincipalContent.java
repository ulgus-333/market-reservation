package com.reservation.authentication.domain.principal.impl;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reservation.authentication.domain.type.Authority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class PrincipalContent {
    private final String authority;
    private final String userIdx;
    private final String marketIdx;

    public static PrincipalContent from(DecodedJWT jwt) {
        return PrincipalContent.builder()
                .authority(getClaimByName(jwt, "authority"))
                .userIdx(jwt.getSubject())
                .marketIdx(getClaimByName(jwt, "marketIdx"))
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private PrincipalContent(String authority, String userIdx, String marketIdx) {
        this.authority = authority;
        this.userIdx = userIdx;
        this.marketIdx = marketIdx;
    }

    private static String getClaimByName(DecodedJWT jwt, String name) {
        Claim claim = jwt.getClaim(name);

        return claim.isNull() ? null : claim.asString();
    }

    public boolean valid() {
        return StringUtils.hasText(this.userIdx);
    }

    public Authority getAuthority() {
        return Authority.findByName(this.authority);
    }
}