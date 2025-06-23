package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.ConsoleIdentifyEntity;
import com.reservation.common.application.executor.CryptoExecutor;

public record ConsoleAdditionalDetailResponse (
        UserDepartmentResponse department,
        String position,
        String name
) implements AdditionalDetailResponse {
    public static ConsoleAdditionalDetailResponse from(ConsoleIdentifyEntity entity) {
        return new ConsoleAdditionalDetailResponse(
                UserDepartmentResponse.from(entity.getDepartment()),
                entity.getPosition(),
                CryptoExecutor.decrypt(entity.getName())
        );
    }
}
