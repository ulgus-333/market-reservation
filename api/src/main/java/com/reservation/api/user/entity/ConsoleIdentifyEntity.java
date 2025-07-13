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
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "console_identify")
@Entity
public class ConsoleIdentifyEntity extends BaseDatetimeEntity {
    @Id
    private Long userIdx;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_idx")
    private DepartmentEntity department;

    @Column(nullable = false, length = 20)
    private String position;

    private ConsoleIdentifyEntity(RoleEntity role, String id, String password, DepartmentEntity department, String position, String name, String email, LocalDateTime regDatetime) {
        this(null, UserEntity.create(role, id, password, name, email, regDatetime), department, position, regDatetime);
    }

    private ConsoleIdentifyEntity(Long userIdx, UserEntity user, DepartmentEntity department, String position, LocalDateTime regDatetime) {
        super(regDatetime);
        this.userIdx = userIdx;
        this.user = user;
        this.department = department;
        this.position = position;
    }
}
