package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.common.application.executor.CryptoExecutor;

public record UserAdditionalDetailResponse (
        String name,
        String phone,
        String nickname,
        String birth
) implements AdditionalDetailResponse {
    public static UserAdditionalDetailResponse from(UserIdentifyEntity userIdentify) {
        return new UserAdditionalDetailResponse(
                CryptoExecutor.decrypt(userIdentify.getName()),
                CryptoExecutor.decrypt(userIdentify.getPhone()),
                userIdentify.getNickname(),
                userIdentify.getBirth()
        );
    }
}
