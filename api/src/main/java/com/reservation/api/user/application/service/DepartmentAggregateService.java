package com.reservation.api.user.application.service;

import com.reservation.api.market.application.service.MarketAggregateService;
import com.reservation.api.market.entity.MarketEntity;
import com.reservation.api.user.application.dto.UserNameMapper;
import com.reservation.api.user.entity.DepartmentEntity;
import com.reservation.api.user.presentation.dto.request.DepartmentCommandRequest;
import com.reservation.api.user.presentation.dto.request.DepartmentsQueryRequest;
import com.reservation.api.user.presentation.dto.response.DepartmentsResponse;
import com.reservation.api.user.repository.DepartmentEntityRepository;
import com.reservation.api.user.repository.custom.DepartmentCustomRepository;
import com.reservation.api.user.repository.dto.query.DepartmentsQueryDto;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.ConflictType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class DepartmentAggregateService {
    private final UserAggregateService userAggregateService;
    private final MarketAggregateService marketAggregateService;
    private final DepartmentEntityRepository departmentEntityRepository;
    private final DepartmentCustomRepository departmentCustomRepository;

    public void registerDepartment(RequestUser requestUser, DepartmentCommandRequest commandRequest) {
        MarketEntity market = marketAggregateService.findUserMarketById(requestUser);

        DepartmentEntity department = DepartmentEntity.insertEntity(market, commandRequest.getName(), commandRequest.getRequestDatetime(), requestUser.getIdxAsLong());

        if (departmentEntityRepository.existsByMarketIdxAndName(market.getIdx(), department.getName())) {
            throw new BusinessException(ConflictType.DEPARTMENT_NAME_EXISTS);
        }
        departmentEntityRepository.save(department);
    }

    public DepartmentsResponse fetchDepartments(RequestUser requestUser, DepartmentsQueryRequest queryRequest) {
        MarketEntity market = marketAggregateService.findUserMarketById(requestUser);

        DepartmentsQueryDto queryDto = queryRequest.toQueryDto(market.getIdx());

        Page<DepartmentEntity> departments = departmentCustomRepository.pagingSearchByQueryDto(queryDto);

        Set<Long> userIdxes = departments.stream()
                .flatMap(department -> Stream.of(department.getRegIdx(), department.getModIdx()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        UserNameMapper userNameMapper = userAggregateService.generateUserNameMapper(userIdxes);

        return DepartmentsResponse.from(departments, userNameMapper);
    }
}
