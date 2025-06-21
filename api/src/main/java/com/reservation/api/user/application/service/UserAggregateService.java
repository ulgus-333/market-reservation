package com.reservation.api.user.application.service;

import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.NotFoundType;
import com.reservation.api.user.entity.UserEntity;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.presentation.dto.response.UserDetailResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.principal.impl.AppRequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAggregateService {
    private final UserEntityRepository userEntityRepository;
    private final UserIdentifyEntityRepository userIdentifyEntityRepository;

    public UserDetailResponse fetchUserDetails(AppRequestUser requestUser) {
        UserIdentifyEntity entity = userIdentifyEntityRepository.findById(requestUser.getUserIdx())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        return UserDetailResponse.from(entity);
    }

    @Transactional
    public void withdrawalUser(RequestUser requestUser) {
        UserEntity userEntity = userEntityRepository.findById(requestUser.getIdxAsLong())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        userEntityRepository.delete(userEntity);
    }
}
