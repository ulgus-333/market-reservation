package com.reservation.common.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ConflictType implements ErrorType {
    //=====================================================
    // Default (0 - 99)
    //=====================================================
    CONFLICT(0, "중복된 데이터입니다."),
    //=====================================================
    // API Module (100 - 2000)
    //=====================================================

    //=====================================================
    // API Module - user (100 - 299)
    //=====================================================
    USER_PHONE_ALREADY_EXISTS(100, "이미 가입된 연락처가 있습니다."),
    USER_EMAIL_ALREADY_EXISTS(101, "이미 가입된 이메일이 있습니다."),
    USER_NICKNAME_ALREADY_EXISTS(102, "중복되는 닉네임이 있습니다."),
    REGISTER_ID_DUPLICATED(103, "해당 아이디는 이미 등록된 아이디입니다."),

    //=====================================================
    // API Module - department (300 - 499)
    //=====================================================
    DEPARTMENT_NAME_EXISTS(300, "동일한 이름으로 등록된 부서가 존재합니다."),
    ;

    private final HttpStatus status = HttpStatus.CONFLICT;
    private final Integer number;
    private final String message;
}
