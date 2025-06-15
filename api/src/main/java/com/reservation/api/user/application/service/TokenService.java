package com.reservation.api.user.application.service;

import com.reservation.api.config.support.crypto.CryptoExecutor;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.request.LoginRequest;
import com.reservation.api.user.presentation.dto.response.TokenResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.authentication.config.support.JwtTokenProvider;
import com.reservation.authentication.config.support.dto.TokenGeneratorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TokenService {
    private final UserEntityRepository userEntityRepository;
    private final JwtTokenProvider tokenProvider;

    public TokenResponse generateAccessToken(LoginRequest request) {
        UserEntity userEntity = userEntityRepository.findById(request.id())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        if (!CryptoExecutor.verifyPassword(request.password(), userEntity.getPassword())) {
            throw new BusinessException(NotFoundType.NOT_FOUND_USER_DATA);
        }

        String accessToken = tokenProvider.generateAccessToken(TokenGeneratorDto.appUser(userEntity.getIdx()));
        String refreshToken = tokenProvider.generateRefreshToken(TokenGeneratorDto.appUser(userEntity.getIdx()));

        return new TokenResponse(accessToken, refreshToken);
    }
}
