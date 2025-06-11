package com.reservation.api.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ConflictType implements ErrorType {
    CONFLICT(0, "중복된 데이터입니다."),
    //==========================================
    // user (1 - 0100)
    //==========================================
    USER_PHONE_ALREADY_EXISTS(1, "이미 가입된 연락처가 있습니다."),
    USER_EMAIL_ALREADY_EXISTS(2, "이미 가입된 이메일이 있습니다."),
    USER_NICKNAME_ALREADY_EXISTS(3, "중복되는 닉네임이 있습니다."),
    REGISTER_ID_DUPLICATED(4, "해당 아이디는 이미 등록된 아이디입니다."),
    ;

    private final HttpStatus status = HttpStatus.CONFLICT;
    private final Integer number;
    private final String message;
}
