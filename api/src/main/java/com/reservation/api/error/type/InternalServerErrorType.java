package com.reservation.api.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum InternalServerErrorType implements ErrorType {
    INTERNAL_SERVER_ERROR(0, "시스템 오류가 발생했습니다."),
    FAILED_TO_SEND_MAIL(1, "메일 발송에 실패했습니다."),
    ;

    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private final Integer number;
    private final String message;
}
