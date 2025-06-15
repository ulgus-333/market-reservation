package com.reservation.api.user.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PasswordVerificationDto extends VerificationDto {
    private final Long userIdx;

    @JsonCreator
    public PasswordVerificationDto(@JsonProperty("userIdx") Long userIdx,
                                   @JsonProperty("token") String token) {
        super(token);
        this.userIdx = userIdx;
    }
}
