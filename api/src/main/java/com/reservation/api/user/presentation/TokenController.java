package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.TokenService;
import com.reservation.api.user.presentation.dto.request.LoginRequest;
import com.reservation.api.user.presentation.dto.response.TokenResponse;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 토큰 발급, 갱신, 검증", description = "토큰의 발급과, 발급된 토큰의 갱신 및 검증 기능 제공")
@RequiredArgsConstructor
@RequestMapping("/user/token")
@RestController
public class TokenController {
    private final TokenService tokenService;

    @Operation(summary = "앱 유저 로그인 토큰 발급", description = "앱 유저의 요청으로 로그인을 위한 토큰을 발급받을 수 있는 기능 제공")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "유저 정보가 없는 경우")
    })
    @PostMapping("/app")
    @RequireRole(Authority.ALL)
    public ResponseEntity<TokenResponse> signup(@RequestBody @Valid LoginRequest request) {

        return ResponseEntity.ok(tokenService.generateAccessToken(request));
    }
}
