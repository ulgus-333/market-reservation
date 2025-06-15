package com.reservation.authentication.domain.type;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Authority {
    ALL,
    USER,
    ADMIN,
    CONSOLE,
    ;

    static final Map<String, Authority> MAPPER;

    static {
        MAPPER = Arrays.stream(Authority.values())
                .collect(Collectors.toMap(value -> value.name().toLowerCase(), Function.identity()));
    }

    public static Authority findByName(String name) {
        if (StringUtils.hasText(name)) {
            return MAPPER.get(name.toLowerCase());
        }
        return ALL;
    }

    public String getRole() {
        return "ROLE_" + this.name().toLowerCase();
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
