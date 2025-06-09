package com.reservation.api.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BadRequestType implements ErrorType {
    BAD_REQUEST(0, "잘못된 요청입니다."),
    FAILED_TO_VALIDATE_REQUEST(1, "잘못된 요청 데이터입니다."),
    USER_NAME_ALREADY_EXISTS(2, "User with this name already exists"),
    USER_PHONE_ALREADY_EXISTS(3, "User with this phone already exists" ),
    USER_EMAIL_ALREADY_EXISTS(4, "User with this email already exists"),
    USER_NICKNAME_ALREADY_EXISTS(4, "User with this nickname already exists"),
    ;

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final Integer number;
    private final String message;
}
