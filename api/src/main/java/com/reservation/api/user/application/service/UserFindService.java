package com.reservation.api.user.application.service;

import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.entity.type.AuthorityType;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserFindService {
    private final UserIdentifyEntityRepository userIdentifyEntityRepository;
    private final RedisExecutor redisExecutor;

    public GenericResponse<String> findUserIdForEmailAndGetUUID(AuthorityType authority, String email) {
        String uuid = UUID.randomUUID().toString();

        if (authority == AuthorityType.USER) {
            UserIdentifyEntity user = userIdentifyEntityRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

            redisExecutor.setValue(RedisKey.USER_FIND_ID_KEY.key(uuid), user.getUser().getId(), RedisKey.USER_FIND_ID_KEY.getDuration());
        }

        return new GenericResponse<>(uuid);
    }
}
