package com.reservation.api.user.repository;

import com.reservation.api.user.entity.AdminIdentifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminIdentifyEntityRepository extends JpaRepository<AdminIdentifyEntity, Long> {
    boolean existsByDepartmentIdx(Long departmentIdx);
}
