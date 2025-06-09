package com.reservation.api.error.dto;

import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.exception.ValidationException;
import com.reservation.api.error.type.ForbiddenType;
import com.reservation.api.error.type.InternalServerErrorType;
import com.reservation.api.error.type.MethodNotAllowedType;
import com.reservation.api.error.type.NotFoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public enum ExceptionConverter {
    NOT_HANDLED_COMMON_EXCEPTION(
            Exception.class,
            (exception) -> new FailureResponse(InternalServerErrorType.INTERNAL_SERVER_ERROR),
            (exception) -> InternalServerErrorType.INTERNAL_SERVER_ERROR.getStatus()
            ),

    BUSINESS_EXCEPTION(
            BusinessException.class,
            (exception) -> ((BusinessException) exception).getFailureResponse(),
            (exception) -> ((BusinessException) exception).getStatus()
    ),

    BIND_EXCEPTION(
            BindException.class,
            (exception) -> new ValidationException(((BindException) exception).getFieldErrors()).getFailureResponse(),
            (exception) -> HttpStatus.BAD_REQUEST
    ),

    VALIDATION_REQUEST_EXCEPTION(
            MethodArgumentNotValidException.class,
            (exception) -> new ValidationException(((MethodArgumentNotValidException) exception).getFieldErrors()).getFailureResponse(),
            (exception) -> HttpStatus.BAD_REQUEST
    ),

    ACCESS_DENIED_EXCEPTION(
            AccessDeniedException.class,
            (exception) -> new FailureResponse(ForbiddenType.CANNOT_ACCESS_RESOURCE),
            (exception) -> ForbiddenType.CANNOT_ACCESS_RESOURCE.getStatus()
            ),

    NO_HANDLER_FOUND_EXCEPTION(
            NoHandlerFoundException.class,
            (exception) -> new FailureResponse(NotFoundType.NOT_FOUND),
            (exception) -> HttpStatus.NOT_FOUND
    ),

    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(
            HttpRequestMethodNotSupportedException.class,
            (exception) -> new FailureResponse(MethodNotAllowedType.METHOD_NOT_ALLOWED),
            (exception) -> HttpStatus.METHOD_NOT_ALLOWED
    ),
    ;

    private static final Map<Class<? extends Exception>, ExceptionConverter> MAPPER = new HashMap<>();

    static {
        Arrays.stream(values())
                .filter(ExceptionConverter::isNotCommonException)
                .forEach(type -> MAPPER.put(type.exceptionClass, type));
    }

    private final Class<? extends Exception> exceptionClass;
    private final Function<Exception, FailureResponse> failureResponseGenerator;
    private final Function<Exception, HttpStatus> httpStatusFinder;

    public static FailureResponse failureResponse(Exception exception) {
        return findByException(exception).failureResponseGenerator.apply(exception);
    }

    public static int httpStatusCode(Exception exception) {
        return findByException(exception).httpStatusFinder.apply(exception).value();
    }

    public static ExceptionConverter findByException(Exception exception) {
        if (exception == null) {
            return NOT_HANDLED_COMMON_EXCEPTION;
        }
        return MAPPER.getOrDefault(exception.getClass(), NOT_HANDLED_COMMON_EXCEPTION);
    }

    private boolean isNotCommonException() {
        return this != NOT_HANDLED_COMMON_EXCEPTION;
    }
}
