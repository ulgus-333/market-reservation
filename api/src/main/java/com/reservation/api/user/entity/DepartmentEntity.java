package com.reservation.api.user.entity;

import com.reservation.api.common.entity.meta.BaseExecutorEntity;
import com.reservation.api.market.entity.MarketEntity;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "department")
@Entity
public class DepartmentEntity extends BaseExecutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_idx")
    private MarketEntity market;

    @Column(nullable = false, length = 20)
    private String name;

    @Builder(access = AccessLevel.PRIVATE)
    public DepartmentEntity(Long idx, MarketEntity market, String name, LocalDateTime regDatetime, Long regIdx) {
        super(regDatetime, regIdx);
        this.idx = idx;
        this.market = market;
        this.name = name;
    }

    public static DepartmentEntity insertEntity(MarketEntity market, String name, LocalDateTime regDatetime, Long regIdx) {
        return DepartmentEntity.builder()
                .market(market)
                .name(name)
                .regDatetime(regDatetime)
                .regIdx(regIdx)
                .build();
    }
}
