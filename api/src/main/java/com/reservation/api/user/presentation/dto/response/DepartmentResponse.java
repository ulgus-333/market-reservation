package com.reservation.api.user.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.api.user.application.dto.UserNameMapper;
import com.reservation.api.user.entity.DepartmentEntity;

import java.time.LocalDateTime;

public record DepartmentResponse(
        Long idx,
        String name,
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime regDatetime,
        String regUserName,
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime lastModifyDatetime,
        String lastModifyUserName
) {
    public static DepartmentResponse of(DepartmentEntity department, UserNameMapper userNameMapper) {
        return new DepartmentResponse(
                department.getIdx(),
                department.getName(),
                department.getRegDatetime(),
                userNameMapper.getDecryptNameOrBlank(department.getRegIdx()),
                department.getModDatetime(),
                userNameMapper.getDecryptNameOrBlank(department.getModIdx())
        );
    }
}
