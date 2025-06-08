package com.reservation.api.user.repository;

import com.reservation.api.user.entity.RoleEntity;
import com.reservation.api.user.entity.type.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByAuthority(AuthorityType authorityType);
}
