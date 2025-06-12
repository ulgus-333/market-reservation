package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.UserRegisterService;
import com.reservation.api.user.presentation.dto.request.UserSignupRequest;
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

@Tag(name = "회원가입", description = "일반 유저의 회원가입 및 상점 어드민 / 백오피스 어드민의 계정 등록 기능 제공")
@RequiredArgsConstructor
@RequestMapping("/user/signup")
@RestController
public class SignupController {

    private final UserRegisterService userRegisterService;

    @Operation(summary = "앱 유저 회원가입", description = "앱 유저의 요청으로 회원가입할 수 있는 기능 제공")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "409", description = "기가입 유저 판단(연락처, 이메일, 닉네임)")
    })
    @PostMapping("/app")
    @RequireRole(Authority.ALL)
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequest request) {

        userRegisterService.signupUser(request);

        return ResponseEntity.ok().build();
    }
}
