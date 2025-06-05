package com.reservation.authentication.domain.principal.impl;

import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.type.Authority;
import com.reservation.authentication.utils.HttpHeaderUtils;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString(callSuper = true)
@Getter
public class ConsoleRequestUser implements RequestUser {
    private final Authority authority = Authority.CONSOLE;
    private final Long userIdx;

    public static ConsoleRequestUser from(PrincipalContent principal) {
        return new ConsoleRequestUser(Long.valueOf(principal.getUserIdx()));
    }

    public ConsoleRequestUser(Long userIdx) {
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
