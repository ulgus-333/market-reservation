package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.ConsoleIdentifyEntity;

public record ConsoleAdditionalDetailResponse (
        UserDepartmentResponse department,
        String position
) implements AdditionalDetailResponse {
    public static ConsoleAdditionalDetailResponse from(ConsoleIdentifyEntity entity) {
        return new ConsoleAdditionalDetailResponse(
                UserDepartmentResponse.from(entity.getDepartment()),
                entity.getPosition()
        );
    }
}
