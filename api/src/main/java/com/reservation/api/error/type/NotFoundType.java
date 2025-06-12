package com.reservation.api.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum NotFoundType implements ErrorType {
    NOT_FOUND(0, "요청하신 URL을 찾을 수 없습니다."),

    //==========================================
    // user (1 - 0100)
    //==========================================
    NOT_FOUND_USER_DATA(1, "유저정보를 찾을 수 없습니다.");

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final Integer number;
    private final String message;
}
