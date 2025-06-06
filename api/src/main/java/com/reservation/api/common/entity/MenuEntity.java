package com.reservation.api.common.entity;

import com.reservation.api.common.entity.meta.BaseDatetimeEntity;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.COMMON_SCHEMA, name = "menu")
@Entity
public class MenuEntity extends BaseDatetimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private Boolean isActive = true;

    public MenuEntity(String name, Boolean isActive, LocalDateTime regDatetime) {
        this(null, name, isActive, regDatetime);
    }

    public MenuEntity(Long id, String name, Boolean isActive, LocalDateTime regDatetime) {
        super(regDatetime);
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }
}
