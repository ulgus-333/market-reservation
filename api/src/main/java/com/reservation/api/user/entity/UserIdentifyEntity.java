package com.reservation.api.user.entity;

import com.reservation.api.common.entity.meta.BaseDatetimeEntity;
import com.reservation.api.user.repository.converter.user.GenderConverter;
import com.reservation.api.user.repository.converter.user.RegisterTypeConverter;
import com.reservation.api.user.entity.type.GenderType;
import com.reservation.api.user.entity.type.RegisterType;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "user_identify")
@Entity
public class UserIdentifyEntity extends BaseDatetimeEntity {
    @Id
    private Long userIdx;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private GenderType gender;

    @Column(nullable = false, length = 10)
    private String birth;

    @Column(nullable = false)
    private String ci;

    @Column(nullable = false)
    private String di;

    @Column(nullable = false)
    @Convert(converter = RegisterTypeConverter.class)
    private RegisterType registerType;

    public static UserIdentifyEntity create(RoleEntity role, String id, String password, String name, String phone, String email, String nickname, GenderType gender, String birth, String ci, String di, RegisterType registerType, LocalDateTime regDatetime) {
        return new UserIdentifyEntity(role, id, password, name, phone, email, nickname, gender, birth, ci, di, registerType, regDatetime);
    }

    private UserIdentifyEntity(RoleEntity role, String id, String password, String name, String phone, String email, String nickname, GenderType gender, String birth, String ci, String di, RegisterType registerType, LocalDateTime regDatetime) {
        this(null, UserEntity.create(role, id, password, regDatetime), name, phone, email, nickname, gender, birth, ci, di, registerType, regDatetime);
    }

    private UserIdentifyEntity(Long userIdx, UserEntity user, String name, String phone, String email, String nickname, GenderType gender, String birth, String ci, String di, RegisterType registerType, LocalDateTime regDatetime) {
        super(regDatetime);
        this.userIdx = userIdx;
        this.user = user;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.ci = ci;
        this.di = di;
        this.registerType = registerType;
    }
}
