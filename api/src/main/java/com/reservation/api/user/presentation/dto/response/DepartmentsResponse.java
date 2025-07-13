package com.reservation.api.user.presentation.dto.response;

import com.reservation.api.user.application.dto.UserNameMapper;
import com.reservation.api.user.entity.DepartmentEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

@Getter
public class DepartmentsResponse {
    private final Page<DepartmentResponse> departments;

    public static DepartmentsResponse from(Page<DepartmentEntity> entities, UserNameMapper nameMapper) {
        if (CollectionUtils.isEmpty(entities.getContent())) {
            return new DepartmentsResponse(Page.empty());
        }

        Page<DepartmentResponse> response = entities.map(entity -> DepartmentResponse.of(
                entity,
                nameMapper.getDecryptNameOrBlank(entity.getRegIdx()),
                nameMapper.getDecryptNameOrBlank(entity.getModIdx())));

        return new DepartmentsResponse(response);
    }

    private DepartmentsResponse(Page<DepartmentResponse> departments) {
        this.departments = departments;
    }
}
