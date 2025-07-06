package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.DepartmentAggregateService;
import com.reservation.api.user.presentation.dto.request.DepartmentCommandRequest;
import com.reservation.authentication.domain.annotation.Authenticated;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.principal.RequestUser;
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

@Tag(name = "부서 관련 기본 CRUD", description = "관리자, 백오피스 유저의 부서와 관련된 기본 CRUD 기능 제공")
@RequiredArgsConstructor
@RequestMapping("/departments")
@RestController
public class DepartmentController {
    private final DepartmentAggregateService departmentAggregateService;

    @Operation(summary = "부서 등록", description = "관리자가 소속한 상점의 부서 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    @RequireRole({Authority.ADMIN, Authority.CONSOLE})
    public ResponseEntity<Void> registerMarketDepartment(@Authenticated RequestUser requestUser,
                                                         @RequestBody @Valid DepartmentCommandRequest commandRequest) {

        departmentAggregateService.registerDepartment(requestUser, commandRequest);

        return ResponseEntity.ok().build();
    }
}
