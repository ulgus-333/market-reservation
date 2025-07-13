package com.reservation.api.user.presentation.dto.request;

import com.reservation.api.common.presentation.dto.OrderDirectionRequest;
import com.reservation.api.common.presentation.dto.PagingRequest;
import com.reservation.api.user.repository.dto.query.DepartmentsQueryDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class DepartmentsQueryRequest extends PagingRequest {
    private final String name;
    private final DepartmentOrderFields orderFields;
    private final OrderDirectionRequest orderDirection;

    public DepartmentsQueryRequest(Integer page, Integer size, String name, String orderFields, String orderDirection) {
        super(page, size);
        this.name = name;
        this.orderFields = DepartmentOrderFields.findByName(orderFields);
        this.orderDirection = OrderDirectionRequest.findByName(orderDirection);
    }

    public DepartmentsQueryDto toQueryDto(Long marketIdx) {
        return new DepartmentsQueryDto(
                marketIdx,
                this.name,
                super.pageable(Sort.by(this.orderDirection.getDirection(), this.orderFields.field))
        );
    }

    @Getter
    @RequiredArgsConstructor
    enum DepartmentOrderFields {
        NAME("name"),
        REGISTRATION_DATETIME("regDatetime"),
        MODIFICATION_DATETIME("modDatetime"),
        ;

        private static final Map<String, DepartmentOrderFields> MAPPER;

        static {
            MAPPER = Arrays.stream(values())
                    .collect(Collectors.toMap(Enum::name, Function.identity()));
        }

        private final String field;

        private static DepartmentOrderFields findByName(String name) {
            if (!StringUtils.hasText(name)) {
                return REGISTRATION_DATETIME;
            }

            return MAPPER.getOrDefault(name.toUpperCase(), REGISTRATION_DATETIME);
        }
    }
}
