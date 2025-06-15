package com.reservation.api.user.application.service;

import com.reservation.api.common.application.service.MailSenderService;
import com.reservation.api.common.application.service.dto.MailSenderDto;
import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.BadRequestType;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.application.dto.IdVerificationDto;
import com.reservation.api.user.application.dto.PasswordVerificationDto;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.request.VerificationRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserCheckService {
    private final MailSenderService mailSenderService;

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

        if (verificationDto.notEqualsToken(request.verificationCode())) {
            throw new BusinessException(BadRequestType.INVALID_VERIFICATION_CODE);
        }

        return GenericResponse.of(verificationDto.getId());
    }

    @Transactional
    public void verificationCodeForResetPassword(VerificationRequest request, LocalDateTime requestDatetime) {
        PasswordVerificationDto verificationDto = redisExecutor.findByKey(request.redisKey(RedisKey.USER_RESET_PASSWORD_KEY), PasswordVerificationDto.class)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_VERIFICATION_DATA));

        if (verificationDto.notEqualsToken(request.verificationCode())) {
            throw new BusinessException(BadRequestType.INVALID_VERIFICATION_CODE);
        }

        UserEntity targetUser = userEntityRepository.findByIdx(verificationDto.getUserIdx())
                        .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        String initPassword = KeyGenerators.string().generateKey();
        targetUser.updatePassword(initPassword, requestDatetime);

        mailSenderService.sendEmail(MailSenderDto.pwInit(targetUser.getEmail(), initPassword));

        redisExecutor.deleteKey(request.redisKey(RedisKey.USER_RESET_PASSWORD_KEY));
    }
}
