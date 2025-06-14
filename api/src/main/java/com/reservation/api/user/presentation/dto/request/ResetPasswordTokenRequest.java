package com.reservation.api.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordTokenRequest(
        @NotBlank String id,
        @NotBlank String email
) {
}
