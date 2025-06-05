package com.reservation.authentication.domain.principal;

import com.reservation.authentication.domain.principal.impl.AdminRequestUser;
import com.reservation.authentication.domain.principal.impl.AppRequestUser;
import com.reservation.authentication.domain.principal.impl.ConsoleRequestUser;
import com.reservation.authentication.domain.principal.impl.PrincipalContent;

import java.util.Map;

public interface RequestUser extends SecurityPrincipal {
    String getIdx();

    Map<String, String> getHeaders();

    static RequestUser from(PrincipalContent principalContent) {
        return switch (principalContent.getAuthority()) {
            case USER -> AppRequestUser.from(principalContent);
            case ADMIN -> AdminRequestUser.from(principalContent);
            case CONSOLE -> ConsoleRequestUser.from(principalContent);
            default -> throw new IllegalStateException("Unexpected value: " + principalContent.getAuthority());
        };
    }
}
