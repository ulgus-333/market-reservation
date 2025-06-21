package com.reservation.common.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum InternalServerErrorType implements ErrorType {
    INTERNAL_SERVER_ERROR(0, "시스템 오류가 발생했습니다."),
    FAILED_TO_SEND_MAIL(1, "메일 발송에 실패했습니다."),
    REDIS_KEY_VALUE_IS_EMPTY(2, "Redis 저장대상 키 또는 값이 존재하지 않습니다."),
    INVALID_REDIS_KEY_PARAMETERS(3, "Redis 키 생성을 위한 파라미터가 올바르지 않습니다."),
    FAILED_TO_ENCRYPT_VALUE(4, "개인정보 암호화에 실패했습니다."),
    FAILED_TO_DECRYPT_VALUE(5, "개인정보 복호화에 실패했습니다."),
    ;

    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private final Integer number;
    private final String message;
}
