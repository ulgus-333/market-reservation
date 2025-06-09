package com.reservation.api.config.support.messageId;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(-105)
public class ResponseBodyFormatFilter implements Filter {
    public static final String MESSAGE_ID_HEADER_NAME = "x-message-id";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String messageId = generateMessageId();

        response.setHeader(MESSAGE_ID_HEADER_NAME, messageId);
        request.setAttribute(MESSAGE_ID_HEADER_NAME, messageId);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        chain.doFilter(request, responseWrapper);

        final byte[] commonResponse = objectMapper.writeValueAsBytes(CommonResponseFormatter.formatted(responseWrapper, messageId));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentLength(commonResponse.length);
        response.getOutputStream().write(commonResponse);
    }

    private String generateMessageId() {
        return UUID.randomUUID().toString();
    }
}
