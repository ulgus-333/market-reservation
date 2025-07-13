package com.reservation.api.user.repository.custom;

import com.reservation.api.user.entity.DepartmentEntity;
import com.reservation.api.user.repository.dto.query.DepartmentsQueryDto;
import org.springframework.data.domain.Page;

public interface DepartmentCustomRepository {
    /**
     * 검색 조건을 통한 부서의 페이징 목록 반환
     * @param queryDto 검색 조건 dto
     * @return 검색 결과 부서 목록
     */
    Page<DepartmentEntity> pagingSearchByQueryDto(DepartmentsQueryDto queryDto);
}
