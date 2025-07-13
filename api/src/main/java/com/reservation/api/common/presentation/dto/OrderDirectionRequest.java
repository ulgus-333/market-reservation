package com.reservation.api.common.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum OrderDirectionRequest {
    ASC(Sort.Direction.ASC),
    DESC(Sort.Direction.DESC),
    ;

    private static final Map<String, OrderDirectionRequest> MAPPER;

    static {
        MAPPER = Arrays.stream(values())
                .collect(Collectors.toMap(Enum::name, Function.identity()));
    }

    private final Sort.Direction direction;

    public static OrderDirectionRequest findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return DESC;
        }

        return MAPPER.getOrDefault(name.toUpperCase(), DESC);
    }
}
