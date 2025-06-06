package com.reservation.api.common.entity.meta;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class BaseDatetimeEntity {
    @Column(updatable = false, nullable = false)
    private LocalDateTime regDatetime;
    private LocalDateTime modDatetime;

    public BaseDatetimeEntity(LocalDateTime regDatetime) {
        this.regDatetime = regDatetime;
    }

    public void updateModDatetime(LocalDateTime requestDatetime) {
        this.modDatetime = requestDatetime;
    }
}
