package com.reservation.common.error.exception;

import com.reservation.common.error.dto.FailureResponse;
import com.reservation.common.error.type.BadRequestType;
import com.reservation.common.error.type.ErrorType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationException extends RuntimeException implements GlobalException {
    private static final ErrorType VALIDATION_ERROR_TYPE = BadRequestType.FAILED_TO_VALIDATE_REQUEST;
    private final List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        super(VALIDATION_ERROR_TYPE.getMessage());
        this.fieldErrors = fieldErrors;
    }

    @Override
    public ErrorType error() {
        return VALIDATION_ERROR_TYPE;
    }

    @Override
    public FailureResponse getFailureResponse() {
        return new FailureResponse(VALIDATION_ERROR_TYPE.getErrorTypeCode(), getMessage());
    }

    @Override
    public String getMessage() {
        if (CollectionUtils.isEmpty(fieldErrors)) {
            return VALIDATION_ERROR_TYPE.getMessage();
        }

        final FieldError error = fieldErrors.getFirst();

        if (fieldErrors.size() == 1) {
            return String.format("%s은(는) %s", error.getField(), error.getDefaultMessage());
        }
        return String.format("%s외 %d개의 잘못된 형식의 요청 데이터가 존재합니다.", error.getField(), fieldErrors.size() - 1);
    }
}
