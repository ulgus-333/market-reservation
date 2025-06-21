package com.reservation.api.user.entity.type;

import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.BadRequestType;
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

    public static RegisterType findByCode(Integer code) {
        if (code == null) {
            throw new BusinessException(BadRequestType.INVALID_USER_REGISTER_TYPE_CODE);
        }

        return CODE_MAPPER.get(code);
    }
}
