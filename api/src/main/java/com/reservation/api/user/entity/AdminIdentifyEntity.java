package com.reservation.api.user.entity;

import com.reservation.api.common.entity.meta.BaseDatetimeEntity;
import com.reservation.api.market.entity.MarketEntity;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "admin_identify")
@Entity
public class AdminIdentifyEntity extends BaseDatetimeEntity {
    @Id
    private Long userIdx;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_idx")
    private MarketEntity market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_idx")
    private DepartmentEntity department;

    @Column(length = 20)
    private String position;

    @Column(length = 50)
    private String identificationNumber;

    @Column(nullable = false, length = 50)
    private String name;

    public static AdminIdentifyEntity create(RoleEntity role, String id, String password, MarketEntity market, DepartmentEntity department, String position, String identificationNumber, String name, String email, LocalDateTime regDatetime) {
        return new AdminIdentifyEntity(role, id, password, market, department, position, identificationNumber, name, email, regDatetime);
    }

    private AdminIdentifyEntity(RoleEntity role, String id, String password, MarketEntity market, DepartmentEntity department, String position, String identificationNumber, String name, String email, LocalDateTime regDatetime) {
        this(null, UserEntity.create(role, id, password, email, regDatetime), market, department, position, identificationNumber, name, regDatetime);
    }

    private AdminIdentifyEntity(Long userIdx, UserEntity user, MarketEntity market, DepartmentEntity department, String position, String identificationNumber, String name, LocalDateTime regDatetime) {
        super(regDatetime);
        this.userIdx = userIdx;
        this.user = user;
        this.market = market;
        this.department = department;
        this.position = position;
        this.identificationNumber = identificationNumber;
        this.name = name;
    }
}
