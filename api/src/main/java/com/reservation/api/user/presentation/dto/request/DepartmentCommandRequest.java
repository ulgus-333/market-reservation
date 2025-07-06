package com.reservation.api.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DepartmentCommandRequest {
    private final LocalDateTime requestDatetime = LocalDateTime.now();

    @NotBlank
    private final String name;

    @JsonCreator
    public DepartmentCommandRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
