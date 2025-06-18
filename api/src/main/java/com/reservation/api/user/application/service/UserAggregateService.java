package com.reservation.api.user.application.service;

import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.NotFoundType;
import com.reservation.api.user.entity.UserIdentifyEntity;
import com.reservation.api.user.presentation.dto.response.UserDetailResponse;
import com.reservation.api.user.repository.UserIdentifyEntityRepository;
import com.reservation.authentication.domain.principal.impl.AppRequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAggregateService {
    private final UserIdentifyEntityRepository userIdentifyEntityRepository;

    public UserDetailResponse fetchUserDetails(AppRequestUser requestUser) {
        UserIdentifyEntity entity = userIdentifyEntityRepository.findById(requestUser.getUserIdx())
                .orElseThrow(() -> new BusinessException(NotFoundType.NOT_FOUND_USER_DATA));

        return UserDetailResponse.from(entity);
    }
}
