package com.reservation.api.user.presentation.dto.response;

public record GenericResponse<T>(
        T result
) {
    public static GenericResponse<Boolean> of(Boolean result) {
        return new GenericResponse<>(result);
    }
}
