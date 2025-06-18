package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.config.support.crypto.CryptoExecutor;
import com.reservation.api.user.entity.UserIdentifyEntity;

public record UserAdditionalDetailResponse (
        String name,
        String phone,
        String nickname,
        String birth
) {
    public static UserAdditionalDetailResponse from(UserIdentifyEntity userIdentify) {
        return new UserAdditionalDetailResponse(
                CryptoExecutor.decrypt(userIdentify.getName()),
                CryptoExecutor.decrypt(userIdentify.getPhone()),
                userIdentify.getNickname(),
                userIdentify.getBirth()
        );
    }
}
