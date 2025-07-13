package com.reservation.api.user.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.api.user.entity.DepartmentEntity;

import java.time.LocalDateTime;

public record DepartmentResponse(
        Long idx,
        String name,
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime regDatetime,
        String regUserId,
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime lastModifyDatetime,
        String lastModifyUserId
) {
    public static DepartmentResponse of(DepartmentEntity department, String regUserId, String lastModifyUserId) {
        return new DepartmentResponse(
                department.getIdx(),
                department.getName(),
                department.getRegDatetime(),
                regUserId,
                department.getModDatetime(),
                lastModifyUserId
        );
    }
}
