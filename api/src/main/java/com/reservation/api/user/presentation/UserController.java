package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.UserAggregateService;
import com.reservation.api.user.presentation.dto.response.UserDetailResponse;
import com.reservation.authentication.domain.annotation.Authenticated;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.principal.impl.AppRequestUser;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련", description = "유저 관련 전반적인 기능 제공")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserAggregateService userAggregateService;

    @Operation(summary = "유저 정보 상세 조회", description = "일반 유저의 상세정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "유저정보 없음")
    })
    @GetMapping("/my")
    @RequireRole({Authority.USER, Authority.ADMIN, Authority.CONSOLE})
    public ResponseEntity<UserDetailResponse> fetchUserDetails(@Authenticated AppRequestUser requestUser) {

        return ResponseEntity.ok(userAggregateService.fetchUserDetails(requestUser));
    }
}
