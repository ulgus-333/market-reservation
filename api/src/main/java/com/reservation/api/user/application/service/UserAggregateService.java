package com.reservation.api.user.application.service;

import com.reservation.api.user.application.dto.UserNameMapper;
import com.reservation.api.user.entity.AdminIdentifyEntity;
import com.reservation.api.user.entity.ConsoleIdentifyEntity;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.presentation.dto.response.UserDetailResponse;
import com.reservation.api.user.repository.AdminIdentifyEntityRepository;
import com.reservation.api.user.repository.ConsoleIdentifyEntityRepository;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.BadRequestType;
import com.reservation.common.error.type.InternalServerErrorType;
import com.reservation.common.error.type.NotFoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAggregateService {
    private final UserEntityRepository userEntityRepository;
    private final UserIdentifyEntityRepository userIdentifyEntityRepository;
    private final AdminIdentifyEntityRepository adminIdentifyEntityRepository;
    private final ConsoleIdentifyEntityRepository consoleIdentifyEntityRepository;

    public UserDetailResponse fetchUserDetails(RequestUser requestUser) {
        return switch (requestUser.getAuthority()) {
            case USER -> {
                UserIdentifyEntity entity = userIdentifyEntityRepository.findById(requestUser.getIdxAsLong())
                        .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));
                yield UserDetailResponse.from(entity);
            }
            case ADMIN -> {
                AdminIdentifyEntity entity = adminIdentifyEntityRepository.findById(requestUser.getIdxAsLong())
                        .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));
                yield UserDetailResponse.from(entity);
            }
            case CONSOLE -> {
                ConsoleIdentifyEntity entity = consoleIdentifyEntityRepository.findById(requestUser.getIdxAsLong())
                        .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));
                yield UserDetailResponse.from(entity);
            }
            default -> throw new BusinessException(BadRequestType.BAD_REQUEST);
        };
    }

    @Transactional
    public void withdrawalUser(RequestUser requestUser) {
        UserEntity userEntity = userEntityRepository.findById(requestUser.getIdxAsLong())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        userEntityRepository.delete(userEntity);
    }

    public UserNameMapper generateUserNameMapper(Set<Long> userIdxes) {
        if (CollectionUtils.isEmpty(userIdxes)) {
            throw new BusinessException(InternalServerErrorType.USER_IDX_MUST_NOT_BE_EMPTY);
        }

        List<UserEntity> userEntities = userEntityRepository.findAllById(userIdxes);

        return UserNameMapper.from(userEntities);
    }
}
