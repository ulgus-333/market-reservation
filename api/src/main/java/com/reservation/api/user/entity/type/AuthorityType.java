package com.reservation.api.user.entity.type;

import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.BadRequestType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum AuthorityType {
    USER,
    ADMIN,
    CONSOLE,
    ;

    private static final Map<String, AuthorityType> MAPPER;

    static {
        MAPPER = Arrays.stream(values())
                .collect(Collectors.toMap(Enum::name, Function.identity()));
    }

    public static AuthorityType fromName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new BusinessException(BadRequestType.INVALID_USER_AUTHORITY_TYPE);
        }

        return MAPPER.get(name.toUpperCase());
    }
}
