package com.reservation.authentication.domain.principal.impl;

import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.type.Authority;
import com.reservation.authentication.utils.HttpHeaderUtils;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString(callSuper = true)
@Getter
public class AppRequestUser implements RequestUser {
    private final Authority authority = Authority.USER;
    private final Long userIdx;

    public static AppRequestUser from(PrincipalContent principal) {
        return new AppRequestUser(Long.valueOf(principal.getUserIdx()));
    }

    public AppRequestUser(Long userIdx) {
        this.userIdx = userIdx;
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
                .build();
    }
}
