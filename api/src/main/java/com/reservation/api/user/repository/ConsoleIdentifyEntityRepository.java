package com.reservation.api.user.repository;

import com.reservation.api.user.entity.ConsoleIdentifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleIdentifyEntityRepository extends JpaRepository<ConsoleIdentifyEntity, Long> {
}
