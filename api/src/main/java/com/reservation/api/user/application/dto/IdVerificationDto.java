package com.reservation.api.user.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IdVerificationDto extends VerificationDto {
    private final String id;

    @JsonCreator
    public IdVerificationDto(@JsonProperty("id") String id,
                             @JsonProperty("token") String token) {
        super(token);
        this.id = id;
    }
}
