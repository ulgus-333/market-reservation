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
public class BaseExecutorEntity extends BaseDatetimeEntity {
    @Column(nullable = false, updatable = false)
    private Long regIdx;
    private Long modIdx;

    public BaseExecutorEntity(LocalDateTime regDatetime, Long regIdx) {
        super(regDatetime);
        this.regIdx = regIdx;
    }

    public void updateMetaFields(Long modIdx, LocalDateTime requestDatetime) {
        super.updateModDatetime(requestDatetime);
        this.modIdx = modIdx;
    }
}
