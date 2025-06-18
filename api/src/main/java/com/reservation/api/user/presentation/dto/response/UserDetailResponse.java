package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.UserIdentifyEntity;

public record UserDetailResponse (
         BasicDetailResponse basic,
         UserAdditionalDetailResponse additional
) {
    public static UserDetailResponse from(UserIdentifyEntity userIdentify) {
        return new UserDetailResponse(
                BasicDetailResponse.from(userIdentify.getUser()),
                UserAdditionalDetailResponse.from(userIdentify)
        );
    }
}
