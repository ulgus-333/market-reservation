package com.reservation.api.common.entity.meta;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Set<Long> metaIdxes() {
        return Stream.of(this.regIdx, this.modIdx)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
