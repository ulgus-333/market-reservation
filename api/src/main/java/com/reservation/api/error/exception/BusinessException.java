package com.reservation.api.error.exception;

import com.reservation.api.error.dto.FailureResponse;
import com.reservation.api.error.type.ErrorType;
import org.apache.commons.lang3.ArrayUtils;

public class BusinessException extends RuntimeException implements GlobalException {
    private final ErrorType error;
    private final String[] messageParameters;

    public BusinessException(ErrorType error) {
        super(error.getMessage());
        this.error = error;
        this.messageParameters = null;
    }

    public BusinessException(ErrorType error, String... message) {
        super(error.getMessage(message));
        this.error = error;
        this.messageParameters = message;
    }

    @Override
    public ErrorType error() {
        return error;
    }

    @Override
    public FailureResponse getFailureResponse() {
        if (messageParameters == null || ArrayUtils.isEmpty(messageParameters)) {
            return error.toFailureResponse();
        }
        return new FailureResponse(error.getErrorTypeCode(), error.getMessage(messageParameters));
    }
}
