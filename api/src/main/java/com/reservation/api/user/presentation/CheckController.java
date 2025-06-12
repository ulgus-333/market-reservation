package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.UserCheckService;
import com.reservation.api.user.presentation.dto.response.GenericResponse;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/check/duplicated/id")
    @RequireRole(Authority.ALL)
    public ResponseEntity<GenericResponse<Boolean>> checkDuplicatedId(@RequestParam("id") @NotBlank String id) {

        return ResponseEntity.ok(userCheckService.checkDuplicateId(id));
    }
}
