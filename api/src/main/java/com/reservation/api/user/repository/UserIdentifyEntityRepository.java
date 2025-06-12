package com.reservation.api.user.repository;

import com.reservation.api.user.entity.UserIdentifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIdentifyEntityRepository extends JpaRepository<UserIdentifyEntity, Long> {
    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<UserIdentifyEntity> findByEmail(java.lang.String email);
}
