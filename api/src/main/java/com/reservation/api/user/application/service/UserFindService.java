package com.reservation.api.user.application.service;

import com.reservation.api.common.application.service.MailSenderService;
import com.reservation.api.common.application.service.dto.MailSenderDto;
import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.application.dto.IdVerificationDto;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserFindService {
    private final MailSenderService mailSenderService;

    private final UserEntityRepository userEntityRepository;
    private final RedisExecutor redisExecutor;

    public GenericResponse<String> findUserIdForEmailAndGetUUID(String email) {
        String uuid = UUID.randomUUID().toString();
        UserEntity user = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        IdVerificationDto idVerificationDto = new IdVerificationDto(user.getId(), KeyGenerators.string().generateKey());
        redisExecutor.setValue(RedisKey.USER_FIND_ID_KEY.key(uuid), idVerificationDto, RedisKey.USER_FIND_ID_KEY.getDuration());

        mailSenderService.sendEmail(MailSenderDto.idSearch(user.getEmail(), idVerificationDto.token()));

        return new GenericResponse<>(uuid);
    }
}
