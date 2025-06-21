package com.reservation.common.error;

import com.reservation.common.error.dto.ExceptionConverter;
import com.reservation.common.error.dto.FailureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalErrorControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponse> handleException(Exception e) {
        return ResponseEntity
                .status(ExceptionConverter.httpStatusCode(e))
                .body(ExceptionConverter.failureResponse(e));
    }
}
