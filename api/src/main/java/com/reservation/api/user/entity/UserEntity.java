package com.reservation.api.user.entity;

import com.reservation.api.common.entity.meta.BaseDatetimeEntity;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "user")
@Entity
public class UserEntity extends BaseDatetimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_idx")
    private RoleEntity role;

    @Column(unique = true, nullable = false, length = 50)
    private String id;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private Boolean isActive;

    public static UserEntity create(RoleEntity role, String id, String password, LocalDateTime regDatetime) {
        return new UserEntity(role, id, password, true, regDatetime);
    }

    private UserEntity(RoleEntity role, String id, String password, Boolean isActive, LocalDateTime regDatetime) {
        this(null, role, id, password, isActive, regDatetime);
    }

    private UserEntity(Long idx, RoleEntity role, String id, String password, Boolean isActive, LocalDateTime regDatetime) {
        super(regDatetime);
        this.idx = idx;
        this.role = role;
        this.id = id;
        this.password = password;
        this.isActive = isActive;
    }
}
