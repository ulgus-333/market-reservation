package com.reservation.api.user.application.service;

import com.reservation.api.market.entity.MarketEntity;
import com.reservation.api.user.entity.DepartmentEntity;
import com.reservation.api.user.presentation.dto.request.DepartmentCommandRequest;
import com.reservation.api.user.repository.DepartmentEntityRepository;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.ConflictType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentAggregateService {
    private final MarketAggregateService marketAggregateService;
    private final DepartmentEntityRepository departmentEntityRepository;

    public void registerDepartment(RequestUser requestUser, DepartmentCommandRequest commandRequest) {
        MarketEntity market = (requestUser.getAuthority().isAdmin())
                ? marketAggregateService.findByIdOrElseThrow(requestUser.getMarketIdx())
                : MarketEntity.console();

        DepartmentEntity department = DepartmentEntity.insertEntity(market, commandRequest.getName(), commandRequest.getRequestDatetime(), requestUser.getIdxAsLong());

        if (departmentEntityRepository.existsByMarketIdxAndName(market.getIdx(), department.getName())) {
            throw new BusinessException(ConflictType.DEPARTMENT_NAME_EXISTS);
        }
        departmentEntityRepository.save(department);
    }
}
