package com.reservation.api.user.application.service;

import com.reservation.common.application.executor.RedisExecutor;
import com.reservation.common.dto.RedisKey;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.ConflictType;
import com.reservation.api.user.entity.RoleEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.entity.type.AuthorityType;
import com.reservation.api.user.presentation.dto.request.UserSignupRequest;
import com.reservation.api.user.repository.RoleEntityRepository;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegisterService {
    private final UserEntityRepository userEntityRepository;
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
        if (userIdentifyEntityRepository.existsByPhone(userIdentify.getPhone())) {
            throw new BusinessException(ConflictType.USER_PHONE_ALREADY_EXISTS);
        }
        if (userEntityRepository.existsByEmail(userIdentify.email())) {
            throw new BusinessException(ConflictType.USER_EMAIL_ALREADY_EXISTS);
        }
        if (userIdentifyEntityRepository.existsByNickname(userIdentify.getNickname())) {
            throw new BusinessException(ConflictType.USER_NICKNAME_ALREADY_EXISTS);
        }
    }
}
