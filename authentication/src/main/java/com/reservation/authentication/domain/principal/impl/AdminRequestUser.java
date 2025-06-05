package com.reservation.authentication.domain.principal.impl;

import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.type.Authority;
import com.reservation.authentication.utils.HttpHeaderUtils;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString(callSuper = true)
@Getter
public class AdminRequestUser implements RequestUser {
    private final Authority authority = Authority.ADMIN;
    private final Long userIdx;
    private final Long marketIdx;

    public static AdminRequestUser from(PrincipalContent principal) {
        return new AdminRequestUser(Long.valueOf(principal.getUserIdx()), Long.valueOf(principal.getMarketIdx()));
    }

    public AdminRequestUser(Long userIdx, Long marketIdx) {
        this.userIdx = userIdx;
        this.marketIdx = marketIdx;
    }

    @Override
    public String getIdx() {
        return String.valueOf(this.userIdx);
    }

    @Override
    public Map<String, String> getHeaders() {
        return HttpHeaderUtils.HeadersBuilder.builder()
                .add(HttpHeaderUtils.XHeaders.AUTHORITY, this.authority.name())
                .add(HttpHeaderUtils.XHeaders.USER_IDX, this.userIdx)
                .add(HttpHeaderUtils.XHeaders.MARKET_IDX, this.marketIdx)
                .build();
    }
}
