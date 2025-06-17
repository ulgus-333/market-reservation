package com.reservation.api.user.application.service;

import com.reservation.api.config.support.crypto.CryptoExecutor;
import com.reservation.api.config.support.redis.RedisExecutor;
import com.reservation.api.config.support.redis.RedisKey;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.request.LoginRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.presentation.dto.response.TokenResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.authentication.config.support.JwtTokenProvider;
import com.reservation.authentication.config.support.dto.TokenGeneratorDto;
import com.reservation.authentication.domain.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TokenService {
    private final UserEntityRepository userEntityRepository;
    private final JwtTokenProvider tokenProvider;
    private final RedisExecutor redisExecutor;

    public TokenResponse generateAccessToken(LoginRequest request) {
        UserEntity userEntity = userEntityRepository.findById(request.id())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        if (!CryptoExecutor.verifyPassword(request.password(), userEntity.getPassword())) {
            throw new BusinessException(NotFoundType.NOT_FOUND_USER_DATA);
        }

        String accessToken = tokenProvider.generateAccessToken(TokenGeneratorDto.appUser(userEntity.getIdx()));
        String refreshToken = tokenProvider.generateRefreshToken(TokenGeneratorDto.appUser(userEntity.getIdx()));

        redisExecutor.setValue(RedisKey.TOKEN_REFRESH.key(String.valueOf(userEntity.getIdx())), String.class, RedisKey.TOKEN_REFRESH.getDuration());

        return new TokenResponse(accessToken, refreshToken);
    }

    public GenericResponse<Boolean> verifyAccessToken(Authority authority, String accessToken, String userIdx) {
        return new GenericResponse<>(tokenProvider.verifyToken(authority, accessToken, userIdx));
    }
}
