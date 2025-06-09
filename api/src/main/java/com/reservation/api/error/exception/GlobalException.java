package com.reservation.api.error.exception;

import com.reservation.api.error.dto.FailureResponse;
import com.reservation.api.error.type.ErrorType;
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
