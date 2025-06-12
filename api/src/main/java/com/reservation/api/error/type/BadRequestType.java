package com.reservation.api.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BadRequestType implements ErrorType {
    BAD_REQUEST(0, "잘못된 요청입니다."),
    FAILED_TO_VALIDATE_REQUEST(1, "잘못된 요청 데이터입니다."),
    INVALID_USER_AUTHORITY_TYPE(2, "올바르지 않은 유저의 인증유형 타입입니다."),
    ;

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final Integer number;
    private final String message;
}
