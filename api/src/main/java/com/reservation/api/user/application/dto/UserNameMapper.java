package com.reservation.api.user.application.dto;

import com.reservation.api.user.entity.UserEntity;
import com.reservation.common.application.executor.CryptoExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserNameMapper {
    private static final String SYSTEM_NAME = "SYSTEM";
    private static final String WITHDRAWAL_USERNAME = "탈퇴한 유저입니다.";

    private final Map<Long, String> MAPPER = new HashMap<>();

    public static UserNameMapper from(List<UserEntity> users) {
        if (CollectionUtils.isEmpty(users)) {
            return new UserNameMapper(Collections.emptyMap());
        }
        return new UserNameMapper(
                users.stream()
                        .collect(Collectors.toMap(UserEntity::getIdx, UserEntity::getName))
        );
    }

    private UserNameMapper(Map<Long, String> mapper) {
        if (!CollectionUtils.isEmpty(mapper)) {
            this.MAPPER.putAll(mapper);
        }
    }

    public String getDecryptNameOrBlank(Long idx) {
        if (idx == null) {
            return null;
        }

        if (idx == 0L) {
            return SYSTEM_NAME;
        }

        String name = MAPPER.getOrDefault(idx, null);

        return StringUtils.isBlank(name)
                ? WITHDRAWAL_USERNAME
                : CryptoExecutor.decrypt(name);
    }
}
