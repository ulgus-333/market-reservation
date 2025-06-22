package com.reservation.api.user.application.service;

import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.presentation.dto.request.LoginRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.presentation.dto.response.TokenResponse;
import com.reservation.api.user.repository.AdminIdentifyEntityRepository;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.authentication.config.support.JwtTokenProvider;
import com.reservation.authentication.config.support.dto.TokenGeneratorDto;
import com.reservation.authentication.domain.type.Authority;
import com.reservation.common.application.executor.CryptoExecutor;
import com.reservation.common.application.executor.RedisExecutor;
import com.reservation.common.dto.RedisKey;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.NotFoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TokenService {
    private final UserEntityRepository userEntityRepository;
    private final AdminIdentifyEntityRepository adminIdentifyEntityRepository;
    private final JwtTokenProvider tokenProvider;
    private final RedisExecutor redisExecutor;

    public TokenResponse generateAccessToken(LoginRequest request) {
        UserEntity userEntity = userEntityRepository.findById(request.id())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        if (!CryptoExecutor.verifyPassword(request.password(), userEntity.getPassword())) {
            throw new BusinessException(NotFoundType.NOT_FOUND_USER_DATA);
        }

        String accessToken = generateAccessToken(userEntity);
        String refreshToken = tokenProvider.generateRefreshToken(TokenGeneratorDto.appUser(userEntity.getIdx()));

        redisExecutor.setValue(RedisKey.TOKEN_REFRESH.key(String.valueOf(userEntity.getIdx())), String.class, RedisKey.TOKEN_REFRESH.getDuration());

        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(UserEntity userEntity) {
        return switch (userEntity.getRole().getAuthority()) {
            case USER -> tokenProvider.generateAccessToken(TokenGeneratorDto.appUser(userEntity.getIdx()));
            case ADMIN -> {
                Long marketIdx = adminIdentifyEntityRepository.findById(userEntity.getIdx())
                        .map(adminIdentifyEntity -> adminIdentifyEntity.getMarket().getIdx())
                        .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_MARKET_DATA));
                yield tokenProvider.generateAccessToken(TokenGeneratorDto.adminUser(userEntity.getIdx(), marketIdx));
            }
            case CONSOLE -> tokenProvider.generateAccessToken(TokenGeneratorDto.consoleUser(userEntity.getIdx()));
        };
    }

    public GenericResponse<Boolean> verifyAccessToken(Authority authority, String accessToken, String userIdx) {
        return new GenericResponse<>(tokenProvider.verifyToken(authority, accessToken, userIdx));
    }
}
