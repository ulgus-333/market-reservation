package com.reservation.common.error.dto;

import com.reservation.common.error.type.ErrorType;
import lombok.Getter;

@Getter
public class FailureResponse {
    private final String type;
    private final String message;

    public FailureResponse(final ErrorType errorType) {
        this(errorType.getErrorTypeCode(), errorType.getMessage());
    }

    public FailureResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
