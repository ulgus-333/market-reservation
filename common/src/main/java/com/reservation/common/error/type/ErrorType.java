package com.reservation.common.error.type;

import com.reservation.common.error.dto.FailureResponse;
import org.springframework.http.HttpStatus;

public interface ErrorType {
    String DOMAIN_NAME = "RESERVATION_API";

    HttpStatus getStatus();
    Integer getNumber();
    String getMessage();

    default String getMessage(String[] messages) {
        if (messages == null || messages.length == 0) {
            return getMessage();
        }
        return messages[0];
    }

    default String getErrorTypeCode() {
        return String.format("%s-%d-%04d", DOMAIN_NAME, getStatus().value(), getNumber());
    }

    default FailureResponse toFailureResponse() {
        return new FailureResponse(this);
    }
}
