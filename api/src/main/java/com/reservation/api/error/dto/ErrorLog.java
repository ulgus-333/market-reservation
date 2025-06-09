package com.reservation.api.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class ErrorLog {
    private final String messageId;
    private final int status;
    private final String httpMethod;
    private final String uri;
    private final String errorCode;
    private final String errorMessage;
    private final String clientIp;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> requestParams;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> requestBody;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<String> stackTrace;

    public ErrorLog(HttpServletRequest request, int statusCode, FailureResponse failureResponse, Map<String, Object> requestBody, List<String> stackTrace) {
        this(messageIdFromRequest(request),
                statusCode,
                request.getMethod(),
                request.getRequestURI(),
                failureResponse.getType(),
                failureResponse.getMessage(),
                clientIpFromRequest(request),
                convertParameterToObjectMap(request.getParameterMap(), HttpMethod.valueOf(request.getMethod())),
                requestBody,
                stackTrace);
    }

    public ErrorLog(String messageId, int status, String httpMethod, String uri, String errorCode, String errorMessage, String clientIp, Map<String, Object> requestParams, Map<String, Object> requestBody, List<String> stackTrace) {
        this.messageId = messageId;
        this.status = status;
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.clientIp = clientIp;
        this.requestParams = requestParams;
        this.requestBody = requestBody;
        this.stackTrace = stackTrace;
    }

    private static String messageIdFromRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getAttribute("x-message-id"))
                .map(Object::toString)
                .orElse(null);
    }

    private static String clientIpFromRequest(HttpServletRequest request) {
        final String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip)) {
            return request.getRemoteAddr();
        }
        return ip;
    }

    private static Map<String, Object> convertParameterToObjectMap(Map<String, String[]> requestParameters, HttpMethod httpMethod) {
        if (!httpMethod.equals(HttpMethod.GET)) {
            return null;
        }

        Map<String, Object> formatted = new HashMap<>();
        requestParameters.forEach((key, value) -> {
            if (value.length == 1) {
                formatted.put(key, value[0]);
            } else {
                formatted.put(key, value);
            }
        });
        return formatted;
    }
}
