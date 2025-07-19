package com.reservation.api.user.application.service;

import com.reservation.api.market.application.service.MarketAggregateService;
import com.reservation.api.market.entity.MarketEntity;
import com.reservation.api.user.application.dto.UserNameMapper;
import com.reservation.api.user.entity.DepartmentEntity;
import com.reservation.api.user.presentation.dto.request.DepartmentCommandRequest;
import com.reservation.api.user.presentation.dto.request.DepartmentsQueryRequest;
import com.reservation.api.user.presentation.dto.response.DepartmentResponse;
import com.reservation.api.user.presentation.dto.response.DepartmentsResponse;
import com.reservation.api.user.repository.DepartmentEntityRepository;
import com.reservation.api.user.repository.custom.DepartmentCustomRepository;
import com.reservation.api.user.repository.dto.query.DepartmentsQueryDto;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.BadRequestType;
import com.reservation.common.error.type.ConflictType;
import com.reservation.common.error.type.NotFoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DepartmentAggregateService {
    private final UserAggregateService userAggregateService;
    private final MarketAggregateService marketAggregateService;
    private final DepartmentEntityRepository departmentEntityRepository;
    private final DepartmentCustomRepository departmentCustomRepository;

    @Transactional
    public void registerDepartment(RequestUser requestUser, DepartmentCommandRequest commandRequest) {
        MarketEntity market = marketAggregateService.findUserMarketById(requestUser);

        DepartmentEntity department = DepartmentEntity.insertEntity(market, commandRequest.getName(), commandRequest.getRequestDatetime(), requestUser.getIdxAsLong());

        if (departmentEntityRepository.existsByMarketIdxAndName(market.getIdx(), department.getName())) {
            throw new BusinessException(ConflictType.DEPARTMENT_NAME_EXISTS);
        }
        departmentEntityRepository.save(department);
    }

    public DepartmentsResponse fetchDepartments(RequestUser requestUser, DepartmentsQueryRequest queryRequest) {
        DepartmentsQueryDto queryDto = queryRequest.toQueryDto(requestUser.getMarketIdx());

        Page<DepartmentEntity> departments = departmentCustomRepository.pagingSearchByQueryDto(queryDto);

        Set<Long> userIdxes = departments.stream()
                .flatMap(department -> department.metaIdxes().stream())
                .collect(Collectors.toSet());

        UserNameMapper userNameMapper = userAggregateService.generateUserNameMapper(userIdxes);

        return DepartmentsResponse.from(departments, userNameMapper);
    }

    public DepartmentResponse fetchDepartment(RequestUser requestUser, Long departmentIdx) {
        DepartmentEntity department = departmentEntityRepository.findByIdxAndMarketIdx(departmentIdx, requestUser.getMarketIdx())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_DEPARTMENT_DATA));

        Set<Long> userIdxes = department.metaIdxes();
        UserNameMapper userNameMapper = userAggregateService.generateUserNameMapper(userIdxes);

        return DepartmentResponse.of(department, userNameMapper);
    }

    @Transactional
    public void deleteDepartment(RequestUser requestUser, Long departmentIdx) {
        DepartmentEntity department = departmentEntityRepository.findByIdxAndMarketIdx(departmentIdx, requestUser.getMarketIdx())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_DEPARTMENT_DATA));

        boolean existsMatchingUser = department.isConsoleDepartment()
                ? userAggregateService.existsConsoleUserMatchingDepartment(departmentIdx)
                : userAggregateService.existsAdminUserMatchingDepartment(departmentIdx);

        if (existsMatchingUser) {
            throw new BusinessException(BadRequestType.CANNOT_DELETE_USING_DEPARTMENT);
        }

        departmentEntityRepository.delete(department);
    }
}
