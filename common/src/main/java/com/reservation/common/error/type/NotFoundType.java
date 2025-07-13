package com.reservation.common.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum NotFoundType implements ErrorType {
    //=====================================================
    // Default (0 - 99)
    //=====================================================
    NOT_FOUND(0, "요청하신 URL을 찾을 수 없습니다."),

    //=====================================================
    // API Module (100 - 2000)
    //=====================================================

    //=====================================================
    // API Module - user (100 - 499)
    //=====================================================
    NOT_FOUND_USER_DATA(100, "유저정보를 찾을 수 없습니다."),
    NOT_FOUND_VERIFICATION_DATA(101, "인증번호의 유효시간이 만료되어 정보를 찾을 수 없습니다."),
    NOT_FOUND_DEPARTMENT_DATA(102, "부서정보를 찾을 수 없습니다."),

    //=====================================================
    // API Module - market (500 - 999)
    //=====================================================
    NOT_FOUND_MARKET_DATA(500, "상점 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final Integer number;
    private final String message;
}
