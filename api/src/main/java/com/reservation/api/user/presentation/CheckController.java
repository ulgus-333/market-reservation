package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.UserCheckService;
import com.reservation.api.user.presentation.dto.request.PasswordCheckRequest;
import com.reservation.api.user.presentation.dto.request.VerificationRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.authentication.domain.annotation.Authenticated;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "유저 유효성 검증", description = "아이디 중복검사 등 유효성 검증 기능 제공")
@Validated
@RequiredArgsConstructor
@RequestMapping("/user/check")
@RestController
public class CheckController {
    private final UserCheckService userCheckService;

    @Operation(summary = "회원가입 대상 아이디 중복 검사", description = "회원가입하고자 하는 대상 아이디의 기가입 여부 중복검사를 하는 기능 제공")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "409", description = "아이디 중복됨")
    })
    @GetMapping("/duplicated/id")
    @RequireRole(Authority.ALL)
    public ResponseEntity<GenericResponse<Boolean>> checkDuplicatedId(@RequestParam("id") @NotBlank String id) {

        return ResponseEntity.ok(userCheckService.checkDuplicateId(id));
    }

    @Operation(summary = "아이디 찾기 인증번호 검증", description = "아이디 찾기를 통해 발급된 인증번호를 검증하는 기능 제공")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "인증번호 검증 실패"),
            @ApiResponse(responseCode = "404", description = "인증번호 존재하지 않음(유효시간 만료)")
    })
    @GetMapping("/verification/code/id")
    @RequireRole(Authority.ALL)
    public ResponseEntity<GenericResponse<String>> verificationCodeForFindId(@ModelAttribute @Valid VerificationRequest request) {

        return ResponseEntity.ok(userCheckService.verificationCodeForFindId(request));
    }

    @Operation(summary = "비밀번호 초기화 인증번호 검증", description = "비밀번호 초리화 통해 발급된 인증번호를 검증하는 기능 제공")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "인증번호 검증 실패"),
            @ApiResponse(responseCode = "404", description = "인증번호 존재하지 않음(유효시간 만료)"),
            @ApiResponse(responseCode = "404", description = "비밀번호 초기화 대상 유저가 존재하지 않을 경우")
    })
    @PostMapping("/verification/code/password")
    @RequireRole(Authority.ALL)
    public ResponseEntity<Void> verificationCodeForResetPassword(@ModelAttribute @Valid VerificationRequest request) {

        LocalDateTime requestDatetime = LocalDateTime.now();
        userCheckService.verificationCodeForResetPassword(request, requestDatetime);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 확인", description = "내정보 조회 및 회원 탈퇴 등 인증 과정에서 비밀번호 검증 기능 제공")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 검증 실패"),
            @ApiResponse(responseCode = "404", description = "유저정보 찾을 수 없음")
    })
    @PostMapping("/password")
    @RequireRole({Authority.USER, Authority.ADMIN, Authority.CONSOLE})
    public ResponseEntity<GenericResponse<Boolean>> checkUserPassword(@Authenticated RequestUser requestUser,
                                                                      @RequestBody @Valid PasswordCheckRequest request) {

        return ResponseEntity.ok(userCheckService.checkPassword(requestUser, request));
    }
}
