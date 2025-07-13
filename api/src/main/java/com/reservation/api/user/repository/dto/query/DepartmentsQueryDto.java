package com.reservation.api.user.repository.dto.query;

import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.reservation.api.user.entity.QDepartmentEntity.departmentEntity;

@Getter
public class DepartmentsQueryDto extends PageRequest {
    private final Long marketIdx;
    private final String name;

    public DepartmentsQueryDto(Long marketIdx, String name, Pageable pageable) {
        super(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        this.marketIdx = marketIdx;
        this.name = name;
    }

    public BooleanBuilder searchName() {
        if (StringUtils.isBlank(this.name)) {
            return new BooleanBuilder();
        }
        return new BooleanBuilder()
                .and(departmentEntity.name.contains(this.name));
    }

    public BooleanBuilder eqMarketIdx() {
        return new BooleanBuilder()
                .and(departmentEntity.market.idx.eq(this.marketIdx));
    }
}
