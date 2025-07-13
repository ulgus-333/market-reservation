package com.reservation.api.user.repository.    custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.api.user.entity.DepartmentEntity;
import com.reservation.api.user.repository.custom.DepartmentCustomRepository;
import com.reservation.api.user.repository.dto.query.DepartmentsQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.reservation.api.user.entity.QDepartmentEntity.departmentEntity;

@RequiredArgsConstructor
@Repository
public class DepartmentCustomRepositoryImpl implements DepartmentCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DepartmentEntity> pagingSearchByQueryDto(DepartmentsQueryDto queryDto) {
        List<DepartmentEntity> departments = queryFactory.selectFrom(departmentEntity)
                .where(queryDto.searchName(), queryDto.eqMarketIdx())
                .fetch();

        return PageableExecutionUtils.getPage(departments, queryDto, departments::size);
    }
}
