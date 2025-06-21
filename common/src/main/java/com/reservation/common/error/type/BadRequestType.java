package com.reservation.common.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BadRequestType implements ErrorType {
    //=====================================================
    // Default (0 - 99)
    //=====================================================
    BAD_REQUEST(0, "잘못된 요청입니다."),
    FAILED_TO_VALIDATE_REQUEST(1, "잘못된 요청 데이터입니다."),

    //=====================================================
    // API Module (100 - 2000)
    //=====================================================

    //=====================================================
    // API Module - user (100 - 500)
    //=====================================================
    INVALID_USER_AUTHORITY_TYPE(100, "올바르지 않은 유저의 인증유형 타입입니다."),
    INVALID_VERIFICATION_CODE(101, "올바르지 않은 인증번호입니다."),
    INVALID_GENDER_TYPE_CODE(102, "올바르지 않은 성별코드입니다."),
    INVALID_GENDER_TYPE_VALUE(103, "올바르지 않은 성별 값입니다."),
    INVALID_USER_REGISTER_TYPE_CODE(104, "올바르지 않은 유저가입 형태 코드입니다."),
    INVALID_USER_REGISTER_TYPE_VALUE(105, "올바르지 않은 유저가입 형태 값입니다."),
    ;

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final Integer number;
    private final String message;
}
