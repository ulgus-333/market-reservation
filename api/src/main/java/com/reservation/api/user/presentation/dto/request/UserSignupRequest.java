package com.reservation.api.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.reservation.api.config.support.crypto.CryptoExecutor;
import com.reservation.api.user.entity.RoleEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.entity.type.GenderType;
import com.reservation.api.user.entity.type.RegisterType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignupRequest {
    @Schema(hidden = true)
    private final LocalDateTime requestDatetime = LocalDateTime.now();

    @NotBlank
    private final String id;
    @NotBlank
    private final String password;
    @NotBlank
    private final String name;
    @NotBlank
    private final String phone;
    @NotBlank @Email
    private final String email;
    @NotBlank
    private final String nickname;
    @NotNull
    private final GenderType gender;
    @NotBlank
    private final String birth;
    @NotBlank
    private final String ci;
    @NotBlank
    private final String di;
    @NotNull
    private final RegisterType registerType = RegisterType.NATIVE;

    public UserIdentifyEntity toEntity(RoleEntity role) {
        return UserIdentifyEntity.create(role,
                this.id,
                CryptoExecutor.encryptPassword(this.password),
                CryptoExecutor.encrypt(this.name),
                CryptoExecutor.encrypt(this.phone.replaceAll("-", "")),
                this.email,
                this.nickname,
                this.gender,
                this.birth,
                this.ci,
                this.di,
                this.registerType,
                this.requestDatetime);
    }

    @JsonCreator
    public UserSignupRequest(String id, String password, String name, String phone, String email, String nickname, GenderType gender, String birth, String ci, String di) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.ci = ci;
        this.di = di;
    }
}
