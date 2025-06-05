package com.reservation.authentication.utils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class HttpHeaderUtils {

    @RequiredArgsConstructor
    public enum XHeaders {
        AUTHORITY("x-authority"),
        USER_IDX("x-userIdx"),
        MARKET_IDX("x-marketIdx"),
        ;

        private final String name;
    }

    public static class HeadersBuilder {
        private final Map<String, String> headers = new LinkedHashMap<>();

        public static HeadersBuilder builder() {
            return new HeadersBuilder();
        }

        public <T> HeadersBuilder add(XHeaders key, T value) {
            if (key != null && StringUtils.hasText(value.toString())) {
                headers.put(key.name(), String.valueOf(value));
            }
            return this;
        }

        public Map<String, String> build() {
            return this.headers;
        }
    }
}
