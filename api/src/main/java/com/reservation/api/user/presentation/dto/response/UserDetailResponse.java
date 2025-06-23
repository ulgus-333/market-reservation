package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.AdminIdentifyEntity;
import com.reservation.api.user.entity.ConsoleIdentifyEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;

public record UserDetailResponse (
         BasicDetailResponse basic,
         AdditionalDetailResponse additional
) {
    public static UserDetailResponse from(UserIdentifyEntity userIdentify) {
        return new UserDetailResponse(
                BasicDetailResponse.from(userIdentify.getUser()),
                UserAdditionalDetailResponse.from(userIdentify)
        );
    }

    public static UserDetailResponse from(AdminIdentifyEntity adminIdentify) {
        return new UserDetailResponse(
                BasicDetailResponse.from(adminIdentify.getUser()),
                AdminAdditionalDetailResponse.from(adminIdentify)
        );
    }

    public static UserDetailResponse from(ConsoleIdentifyEntity consoleIdentify) {
        return new UserDetailResponse(
                BasicDetailResponse.from(consoleIdentify.getUser()),
                ConsoleAdditionalDetailResponse.from(consoleIdentify)
        );
    }
}
