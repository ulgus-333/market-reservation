package com.reservation.api.user.application.service;

import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.BadRequestType;
import com.reservation.api.user.entity.RoleEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.entity.type.AuthorityType;
import com.reservation.api.user.presentation.dto.request.UserSignupRequest;
import com.reservation.api.user.repository.RoleEntityRepository;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegisterService {
    private final UserIdentifyEntityRepository userIdentifyEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final RedisExecutor redisExecutor;

    @Transactional
    public void signupUser(UserSignupRequest request) {
        String authorityKey = RedisKey.USER_AUTHORITY.key(AuthorityType.USER.name());
        RoleEntity roleEntity = redisExecutor.findByKey(authorityKey, RoleEntity.class)
                .orElseGet(() -> {
                    RoleEntity role = roleEntityRepository.findByAuthority(AuthorityType.USER);
                    redisExecutor.setValue(authorityKey, role, RedisKey.USER_AUTHORITY.getDuration());
                    return role;
                });

        UserIdentifyEntity userIdentify = request.toEntity(roleEntity);

        checkExistsUserUniqueValue(userIdentify);

        userIdentifyEntityRepository.save(userIdentify);
    }

    private void checkExistsUserUniqueValue(UserIdentifyEntity userIdentify) {
        if (userIdentifyEntityRepository.existsByName(userIdentify.getName())) {
            throw new BusinessException(BadRequestType.USER_NAME_ALREADY_EXISTS);
        }
        if (userIdentifyEntityRepository.existsByPhone(userIdentify.getPhone())) {
            throw new BusinessException(BadRequestType.USER_PHONE_ALREADY_EXISTS);
        }
        if (userIdentifyEntityRepository.existsByEmail(userIdentify.getEmail())) {
            throw new BusinessException(BadRequestType.USER_EMAIL_ALREADY_EXISTS);
        }
        if (userIdentifyEntityRepository.existsByNickname(userIdentify.getNickname())) {
            throw new BusinessException(BadRequestType.USER_NICKNAME_ALREADY_EXISTS);
        }
    }
}
