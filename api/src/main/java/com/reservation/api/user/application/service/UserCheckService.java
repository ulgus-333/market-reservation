package com.reservation.api.user.application.service;

import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.api.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserCheckService {
    private final UserEntityRepository userEntityRepository;

    public GenericResponse<Boolean> checkDuplicateId(String id) {
        Boolean duplicated = userEntityRepository.findById(id)
                .isPresent();

        return GenericResponse.of(duplicated);
    }
}
