package com.reservation.common.config.aop;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reservation.common.error.dto.ErrorLog;
import com.reservation.common.error.dto.ExceptionConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Aspect
public class ExceptionAOP {
    private static final ObjectMapper MULTIPART_IGNORE_MAPPER;
    private static final Integer STACK_TRACE_MAX_LENGTH = 30;

    static {
        MULTIPART_IGNORE_MAPPER = new ObjectMapper()
                .registerModule(MultipartFileIgnoreSerializer.getModule())
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Pointcut("execution(* com.reservation.*.*.presentation..*.*(..))")
    private void pointCut() {}

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    private void logError(JoinPoint joinPoint, Exception exception) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final int httpStatusCode = ExceptionConverter.httpStatusCode(exception);

        final ErrorLog errorLog = new ErrorLog(
                request,
                httpStatusCode,
                ExceptionConverter.failureResponse(exception),
                RequestBodyConverter.convert(request, joinPoint),
                stackTraceForm(exception, httpStatusCode)
        );

        logError(errorLog, joinPoint);
    }

    private static void logError(ErrorLog errorLog, JoinPoint joinPoint) {
        try {
            log.error(MULTIPART_IGNORE_MAPPER.writeValueAsString(errorLog));
        } catch (JsonProcessingException e) {
            log.error("[LOGGING ERROR FAILED] JsonProcessingException: {}", joinPoint.getSignature().getName().getClass());
        }
    }

    private static List<String> stackTraceForm(Exception exception, int httpStatusCode) {
        if (exception == null || httpStatusCode != HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return null;
        }

        return Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .limit(STACK_TRACE_MAX_LENGTH)
                .toList();
    }

    @JsonIgnoreType
    private static class IgnoreMultipartFileMixIn {}
}
