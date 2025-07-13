package com.reservation.api.common.presentation.dto;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class PagingRequest {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 20;

    private final Integer page;
    private final Integer size;

    public PagingRequest(Integer page, Integer size) {
        this.page = validateValue(page, DEFAULT_PAGE);
        this.size = validateValue(size, DEFAULT_SIZE);
    }

    private static int validateValue(Integer value, Integer defaultValue) {
        if (value == null || value < 1) {
            return defaultValue;
        }
        return value;
    }

    public Pageable pageable(Sort sort) {
        return PageRequest.of(this.page - 1, this.size, sort);
    }
}
