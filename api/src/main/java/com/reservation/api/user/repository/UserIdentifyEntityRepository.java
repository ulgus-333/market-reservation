package com.reservation.api.user.repository;

import com.reservation.api.user.entity.UserIdentifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentifyEntityRepository extends JpaRepository<UserIdentifyEntity, Long> {
}
