package com.reservation.api.user.application.service;

import com.reservation.api.common.application.service.MailSenderService;
import com.reservation.api.common.application.service.dto.MailSenderDto;
import com.reservation.common.application.executor.RedisExecutor;
import com.reservation.common.dto.RedisKey;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.NotFoundType;
import com.reservation.api.user.application.dto.IdVerificationDto;
import com.reservation.api.user.application.dto.PasswordVerificationDto;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.request.ResetPasswordTokenRequest;
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

    public GenericResponse<String> generateTokenForFindIdAndSendEmail(String email) {
        UserEntity user = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        String uuid = UUID.randomUUID().toString();
        IdVerificationDto idVerificationDto = new IdVerificationDto(user.getId(), KeyGenerators.string().generateKey());
        redisExecutor.setValue(RedisKey.USER_FIND_ID.key(uuid), idVerificationDto, RedisKey.USER_FIND_ID.getDuration());

        mailSenderService.sendEmail(MailSenderDto.idSearch(user.getEmail(), idVerificationDto.getToken()));

        return new GenericResponse<>(uuid);
    }

    public GenericResponse<String> generateTokenForResetPasswordAndSendEmail(ResetPasswordTokenRequest request) {
        UserEntity user = userEntityRepository.findByIdAndEmail(request.id(), request.email())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        String uuid = UUID.randomUUID().toString();
        PasswordVerificationDto verificationDto = new PasswordVerificationDto(user.getIdx(), KeyGenerators.string().generateKey());
        redisExecutor.setValue(RedisKey.USER_RESET_PASSWORD.key(uuid), verificationDto, RedisKey.USER_RESET_PASSWORD.getDuration());

        mailSenderService.sendEmail(MailSenderDto.pwReset(user.getEmail(), verificationDto.getToken()));

        return new GenericResponse<>(uuid);
    }
}
