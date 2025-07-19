package com.reservation.api.user.repository;

import com.reservation.api.user.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentEntityRepository extends JpaRepository<DepartmentEntity, Long> {

    boolean existsByMarketIdxAndName(Long idx, String name);

    Optional<DepartmentEntity> findByIdxAndMarketIdx(Long idx, Long marketIdx);
}
