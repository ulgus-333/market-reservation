package com.reservation.api.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordCheckRequest (
        @NotBlank String password
) {
}
