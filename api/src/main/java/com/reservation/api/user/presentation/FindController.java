package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.UserFindService;
import com.reservation.api.user.presentation.dto.request.ResetPasswordTokenRequest;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 아이디 / 비밀번호 등 찾기", description = "유저의 아이디 찾기 및 비밀번호 초기화를 위한 기능 제공")
@Validated
@RequiredArgsConstructor
@RequestMapping("/user/find")
@RestController
public class FindController {

    private final UserFindService userFindService;

    @Operation(summary = "유저의 아이디 찾기 인증코드 발급", description = "이메일을 통해 아이디를 찾고, 해당 이메일로 인증번호를 발송하는 기능을 제공한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "찾고자 하는 계정의 정보가 없을 경우")
    })
    @GetMapping("/id")
    @RequireRole(Authority.ALL)
    public ResponseEntity<GenericResponse<String>> generateTokenForFindId(@RequestParam(value = "email") @NotBlank @Email String email) {

        return ResponseEntity.ok(userFindService.generateTokenForFindIdAndSendEmail(email));
    }

    @Operation(summary = "유저 비밀번호 초기화 인증코드 발급", description = "아이디, 이메일을 통해 계정 검증을 하고, 해당 이메일로 인증번호를 발송하는 기능을 제공한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "찾고자 하는 계정의 정보가 없을 경우")
    })
    @GetMapping("/password")
    @RequireRole(Authority.ALL)
    public ResponseEntity<GenericResponse<String>> generateTokenForResetPassword(@ModelAttribute @Valid ResetPasswordTokenRequest request) {

        return ResponseEntity.ok(userFindService.generateTokenForResetPasswordAndSendEmail(request));
    }
}

