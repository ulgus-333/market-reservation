package com.reservation.api.user.application.service;

import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.BadRequestType;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.application.dto.IdVerificationDto;
import com.reservation.api.user.presentation.dto.request.VerificationRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserCheckService {
    private final UserEntityRepository userEntityRepository;
    private final RedisExecutor redisExecutor;

    public GenericResponse<Boolean> checkDuplicateId(String id) {
        Boolean duplicated = userEntityRepository.findById(id)
                .isPresent();

        return GenericResponse.of(duplicated);
    }

    public GenericResponse<String> verificationCodeForFindId(VerificationRequest request) {
        IdVerificationDto verificationDto = redisExecutor.findByKey(request.redisKey(RedisKey.USER_FIND_ID_KEY), IdVerificationDto.class)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_VERIFICATION_DATA));

        if (!verificationDto.equalsToken(request.verificationCode())) {
            throw new BusinessException(BadRequestType.INVALID_VERIFICATION_CODE);
        }

        return GenericResponse.of(verificationDto.id());
    }
}
