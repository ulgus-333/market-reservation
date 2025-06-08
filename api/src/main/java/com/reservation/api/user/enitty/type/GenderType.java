package com.reservation.api.user.enitty.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum GenderType {
    MALE(1),
    FEMALE(2),
    ;

    private static final Map<Integer, GenderType> CODE_MAPPER;

    static {
        CODE_MAPPER = Arrays.stream(values())
                .collect(Collectors.toMap(value -> value.code, Function.identity()));
    }

    private final Integer code;

    public static GenderType findByCode(Integer code) {
        if (code == null) {
            throw new RuntimeException("Invalid gender code");
        }

        return CODE_MAPPER.get(code);
    }
}
