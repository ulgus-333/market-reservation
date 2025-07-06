package com.reservation.api.user.application.service;

import com.reservation.api.market.entity.MarketEntity;
import com.reservation.api.user.repository.MarketEntityRepository;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.NotFoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MarketAggregateService {
    private final MarketEntityRepository marketEntityRepository;

    public MarketEntity findByIdOrElseThrow(Long marketIdx) {
        return marketEntityRepository.findById(marketIdx)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_MARKET_DATA));
    }
}
