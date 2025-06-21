package com.reservation.api.user.application.service;

import com.reservation.api.common.application.service.MailSenderService;
import com.reservation.api.common.application.service.dto.MailSenderDto;
import com.reservation.common.application.executor.RedisExecutor;
import com.reservation.common.dto.RedisKey;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.BadRequestType;
import com.reservation.common.error.type.ConflictType;
import com.reservation.common.error.type.NotFoundType;
import com.reservation.api.user.application.dto.IdVerificationDto;
import com.reservation.api.user.application.dto.PasswordVerificationDto;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.request.PasswordCheckRequest;
import com.reservation.api.user.presentation.dto.request.VerificationRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.common.application.executor.CryptoExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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
        IdVerificationDto verificationDto = redisExecutor.findByKey(request.redisKey(RedisKey.USER_FIND_ID), IdVerificationDto.class)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_VERIFICATION_DATA));

        if (verificationDto.notEqualsToken(request.verificationCode())) {
            throw new BusinessException(BadRequestType.INVALID_VERIFICATION_CODE);
        }

        redisExecutor.deleteKey(request.redisKey(RedisKey.USER_FIND_ID));
        return GenericResponse.of(verificationDto.getId());
    }

    @Transactional
    public void verificationCodeForResetPassword(VerificationRequest request, LocalDateTime requestDatetime) {
        PasswordVerificationDto verificationDto = redisExecutor.findByKey(request.redisKey(RedisKey.USER_RESET_PASSWORD), PasswordVerificationDto.class)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_VERIFICATION_DATA));

        if (verificationDto.notEqualsToken(request.verificationCode())) {
            throw new BusinessException(BadRequestType.INVALID_VERIFICATION_CODE);
        }

        UserEntity targetUser = userEntityRepository.findByIdx(verificationDto.getUserIdx())
                        .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        String initPassword = KeyGenerators.string().generateKey();
        targetUser.updatePassword(initPassword, requestDatetime);

        mailSenderService.sendEmail(MailSenderDto.pwInit(targetUser.getEmail(), initPassword));

        redisExecutor.deleteKey(request.redisKey(RedisKey.USER_RESET_PASSWORD));
    }

    public GenericResponse<String> checkEmailAndSendVerificationCode(String email) {
        if (userEntityRepository.existsByEmail(email)) {
            throw new BusinessException(ConflictType.USER_EMAIL_ALREADY_EXISTS);
        }

        String verificationCode = KeyGenerators.string().generateKey();
        mailSenderService.sendEmail(MailSenderDto.verifyEmail(email, verificationCode));

        String uuid = UUID.randomUUID().toString();
        redisExecutor.setValue(RedisKey.USER_VERIFY_EMAIL.key(uuid), verificationCode, RedisKey.USER_VERIFY_EMAIL.getDuration());

        return GenericResponse.of(uuid);
    }

    public GenericResponse<Boolean> verificationCodeForVerifyEmail(VerificationRequest request) {
        String verificationCode = redisExecutor.findByKey(request.redisKey(RedisKey.USER_VERIFY_EMAIL))
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_VERIFICATION_DATA));

        redisExecutor.deleteKey(request.redisKey(RedisKey.USER_VERIFY_EMAIL));
        return GenericResponse.of(verificationCode.equals(request.verificationCode()));
    }

    public GenericResponse<Boolean> checkPassword(RequestUser requestUser, PasswordCheckRequest request) {
        UserEntity userEntity = userEntityRepository.findById(requestUser.getIdxAsLong())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        return GenericResponse.of(CryptoExecutor.verifyPassword(request.password(), userEntity.getPassword()));
    }
}
