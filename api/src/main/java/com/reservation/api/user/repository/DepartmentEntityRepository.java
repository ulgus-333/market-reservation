package com.reservation.api.user.repository;

import com.reservation.api.user.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentEntityRepository extends JpaRepository<DepartmentEntity, Long> {

    @Query(value = """
        SELECT EXISTS (
            SELECT department.idx
            FROM   DepartmentEntity department
            WHERE  department.market.idx = :idx
              AND  department.name = :name
        )
    """)
    boolean existsByMarketIdxAndName(Long idx, String name);

    Optional<DepartmentEntity> findByIdxAndMarketIdx(Long idx, Long marketIdx);
}
