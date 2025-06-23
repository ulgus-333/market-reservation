package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.entity.DepartmentEntity;

public record UserDepartmentResponse(
        Long idx,
        String name
) {
    public static UserDepartmentResponse from(DepartmentEntity department) {
        return new UserDepartmentResponse(department.getIdx(), department.getName());
    }
}
