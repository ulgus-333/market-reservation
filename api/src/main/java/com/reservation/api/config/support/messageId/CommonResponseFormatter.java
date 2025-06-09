package com.reservation.api.config.support.messageId;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonResponseFormatter {
    private static final String MESSAGE_ID = "messageId";
    private static final String SUCCESS_DATA = "data";
    private static final String FAILURE_DATA = "error";

    static Map<String, Object> formatted(ContentCachingResponseWrapper responseWrapper, String messageId) {
        Map<String, Object> result = new HashMap<>();
        result.put(MESSAGE_ID, messageId);

        HttpStatus status = HttpStatus.valueOf(responseWrapper.getStatus());
        if (status.is2xxSuccessful()) {
            result.put(SUCCESS_DATA, successDataForm(status, responseWrapper));
        } else {
            result.put(FAILURE_DATA, dataForm(responseWrapper));
        }

        return result;
    }

    private static Object successDataForm(HttpStatus status, ContentCachingResponseWrapper responseWrapper) {
        if (status == HttpStatus.CREATED) {
            return null;
        }
        return dataForm(responseWrapper);
    }

    private static Object dataForm(ContentCachingResponseWrapper responseWrapper) {
        try {
            return new ObjectMapper().readValue(responseWrapper.getContentAsByteArray(), Object.class);
        } catch (IOException e) {
            byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
            return contentAsByteArray.length == 0 ? null : new String(contentAsByteArray);
        }
    }
}
