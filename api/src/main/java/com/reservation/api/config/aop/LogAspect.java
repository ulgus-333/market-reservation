package com.reservation.api.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.api.config.support.messageId.ResponseBodyFormatFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class LogAspect {
    private final ObjectMapper objectMapper;

    @Around("within(com.reservation.api.*.presentation..*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String messageId = (String) request.getAttribute(ResponseBodyFormatFilter.MESSAGE_ID_HEADER_NAME);

        long startTime = System.currentTimeMillis();

        try {
            final RequestLog logEntity = RequestLog.of(request, joinPoint.getArgs(), messageId);
            log.info(objectMapper.writeValueAsString(logEntity));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("messageId: {} - Duration Time: {} ms", messageId, System.currentTimeMillis() - startTime);

        return joinPoint.proceed();
    }

    @Getter
    @ToString
    private static class RequestLog {
        private final String messageId;
        private final String requestUrl;
        private final String requestUri;
        private final String method;
        private final String ip;
        private final String data;

        public static RequestLog of(HttpServletRequest request, Object[] arguments, String messageId) {
            return new RequestLog(request, arguments, messageId);
        }

        private RequestLog(HttpServletRequest request, Object[] arguments, String messageId) {
            this.requestUrl = request.getRequestURL().toString();
            this.requestUri = request.getRequestURI();
            this.method = request.getMethod();
            this.ip = request.getRemoteAddr();
            this.data = Arrays.toString(arguments);
            this.messageId = messageId;
        }
    }
}
