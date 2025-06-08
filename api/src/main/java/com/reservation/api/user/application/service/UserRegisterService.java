package com.reservation.api.user.application.service;

import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.user.entity.RoleEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.entity.type.AuthorityType;
import com.reservation.api.user.presentation.dto.request.UserSignupRequest;
import com.reservation.api.user.repository.RoleEntityRepository;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegisterService {
    private final UserIdentifyEntityRepository userIdentifyEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final RedisExecutor redisExecutor;

    public void signupUser(UserSignupRequest request) {
        String authorityKey = RedisKey.USER_AUTHORITY.key(AuthorityType.USER.name());
        RoleEntity roleEntity = redisExecutor.findByKey(authorityKey, RoleEntity.class)
                .orElseGet(() -> {
                    RoleEntity role = roleEntityRepository.findByAuthority(AuthorityType.USER);
                    redisExecutor.setValue(authorityKey, role, RedisKey.USER_AUTHORITY.getDuration());
                    return role;
                });

        UserIdentifyEntity userIdentify = request.toEntity(roleEntity);

        userIdentifyEntityRepository.save(userIdentify);
    }
}
