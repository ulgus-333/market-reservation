package com.reservation.api.user.repository.    custom.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.api.user.entity.DepartmentEntity;
import com.reservation.api.user.repository.custom.DepartmentCustomRepository;
import com.reservation.api.user.repository.dto.query.DepartmentsQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.reservation.api.user.entity.QDepartmentEntity.departmentEntity;

@Repository
public class DepartmentCustomRepositoryImpl extends QuerydslRepositorySupport implements DepartmentCustomRepository {
    private final JPAQueryFactory queryFactory;

    public DepartmentCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DepartmentEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<DepartmentEntity> pagingSearchByQueryDto(DepartmentsQueryDto queryDto) {
        JPAQuery<DepartmentEntity> departments = queryFactory.selectFrom(departmentEntity)
                .where(queryDto.searchName(), queryDto.eqMarketIdx());

        List<DepartmentEntity> pagingDepartments = getQuerydsl().applyPagination(queryDto, departments).fetch();

        return PageableExecutionUtils.getPage(pagingDepartments, queryDto, pagingDepartments::size);
    }
}
