package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.AdminIdentifyEntity;
import com.reservation.common.application.executor.CryptoExecutor;

public record AdminAdditionalDetailResponse (
        UserDepartmentResponse department,
        String position,
        String identificationNumber,
        String name
) implements AdditionalDetailResponse {

    public static AdminAdditionalDetailResponse from(AdminIdentifyEntity entity) {
        return new AdminAdditionalDetailResponse(
                UserDepartmentResponse.from(entity.getDepartment()),
                entity.getPosition(),
                entity.getIdentificationNumber(),
                CryptoExecutor.decrypt(entity.getName())
        );
    }
}
