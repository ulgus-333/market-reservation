package com.reservation.common.error.exception;

import com.reservation.common.error.dto.FailureResponse;
import com.reservation.common.error.type.ErrorType;
import org.springframework.http.HttpStatus;

public interface GlobalException {
    ErrorType error();

    default FailureResponse getFailureResponse() {
        return error().toFailureResponse();
    }

    default HttpStatus getStatus() {
        return error().getStatus();
    }

    default String getCode() {
        return error().getErrorTypeCode();
    }
}
