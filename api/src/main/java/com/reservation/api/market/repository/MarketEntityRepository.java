package com.reservation.api.market.repository;

import com.reservation.api.market.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketEntityRepository extends JpaRepository<MarketEntity, Long> {
}
