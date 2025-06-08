package com.reservation.api.user.enitty.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum RegisterType {
    NATIVE(1),
    SOCIAL(2),
    ;

    private static final Map<Integer, RegisterType> CODE_MAPPER;

    static {
        CODE_MAPPER = Arrays.stream(values())
                .collect(Collectors.toMap(value -> value.code, Function.identity()));
    }

    private final Integer code;

    public static final RegisterType findByCode(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("Code cannot be null");
        }

        return CODE_MAPPER.get(code);
    }
}
