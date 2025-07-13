package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.AdminIdentifyEntity;

public record AdminAdditionalDetailResponse (
        UserDepartmentResponse department,
        String position,
        String identificationNumber
) implements AdditionalDetailResponse {

    public static AdminAdditionalDetailResponse from(AdminIdentifyEntity entity) {
        return new AdminAdditionalDetailResponse(
                UserDepartmentResponse.from(entity.getDepartment()),
                entity.getPosition(),
                entity.getIdentificationNumber()
        );
    }
}
