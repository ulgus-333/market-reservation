package com.reservation.api.user.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.api.user.entity.UserEntity;

import java.time.LocalDateTime;

public record BasicDetailResponse(
        String id,
        String email,
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime regDatetime
) {
    public static BasicDetailResponse from(UserEntity user) {
        return new BasicDetailResponse(
                user.getId(),
                user.getEmail(),
                user.getRegDatetime()
        );
    }
}
