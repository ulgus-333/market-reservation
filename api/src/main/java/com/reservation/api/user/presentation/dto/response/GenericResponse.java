package com.reservation.api.user.presentation.dto.response;

public record GenericResponse<T>(
        T result
) {
    public static <T> GenericResponse<T> of(T result) {
        return new GenericResponse<>(result);
    }
}
