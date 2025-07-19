package com.reservation.api.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DepartmentCommandRequest {
    @Schema(hidden = true)
    private final LocalDateTime requestDatetime = LocalDateTime.now();

    @NotBlank
    private final String name;

    @JsonCreator
    public DepartmentCommandRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
