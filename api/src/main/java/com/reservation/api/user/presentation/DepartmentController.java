package com.reservation.api.user.presentation;

import com.reservation.api.user.application.service.DepartmentAggregateService;
import com.reservation.api.user.presentation.dto.request.DepartmentCommandRequest;
import com.reservation.api.user.presentation.dto.request.DepartmentsQueryRequest;
import com.reservation.api.user.presentation.dto.response.DepartmentResponse;
import com.reservation.api.user.presentation.dto.response.DepartmentsResponse;
import com.reservation.authentication.domain.annotation.Authenticated;
import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "부서 관련 기본 CRUD", description = "관리자, 백오피스 유저의 부서와 관련된 기본 CRUD 기능 제공")
@RequiredArgsConstructor
@Validated
@RequireRole({Authority.ADMIN, Authority.CONSOLE})
@RequestMapping("/departments")
@RestController
public class DepartmentController {
    private final DepartmentAggregateService departmentAggregateService;

    @Operation(summary = "부서 등록", description = "관리자, 백오피스 유저가 소속한 상점의 부서 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "상점 정보 없음"),
            @ApiResponse(responseCode = "409", description = "동일한 부서 명칭 존재")
    })
    @PostMapping
    public ResponseEntity<Void> registerMarketDepartment(@Schema(hidden = true) @Authenticated RequestUser requestUser,
                                                         @RequestBody @Valid DepartmentCommandRequest commandRequest) {

        departmentAggregateService.registerDepartment(requestUser, commandRequest);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "부서 목록 조회", description = "관리자, 백오피스 유저가 소속한 상점의 부서 페이징 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공(목록이 없을 경우 포함)"),
            @ApiResponse(responseCode = "404", description = "상점 정보 없음")
    })
    @GetMapping
    public ResponseEntity<DepartmentsResponse> fetchDepartments(@Schema(hidden = true) @Authenticated RequestUser requestUser,
                                                                @ModelAttribute @Valid DepartmentsQueryRequest queryRequest) {

        return ResponseEntity.ok(departmentAggregateService.fetchDepartments(requestUser, queryRequest));
    }

    @Operation(summary = "부서 상세 조회", description = "관리자, 백오피스 유저가 소속한 상점의 부서의 상세정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "부서 정보 없음")
    })
    @GetMapping("/{departmentIdx}")
    public ResponseEntity<DepartmentResponse> fetchDepartment(@Schema(hidden = true) @Authenticated RequestUser requestUser,
                                                              @PathVariable @NotNull Long departmentIdx) {

        return ResponseEntity.ok(departmentAggregateService.fetchDepartment(requestUser, departmentIdx));
    }

    @Operation(summary = "부서 삭제", description = "관리자, 백오피스 유저가 소속한 상점의 부서 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "부서를 사용중인 유저 존재"),
            @ApiResponse(responseCode = "404", description = "부서 정보 없음")
    })
    @DeleteMapping("/{departmentIdx}")
    public ResponseEntity<Void> deleteDepartment(@Schema(hidden = true) @Authenticated RequestUser requestUser,
                                                 @PathVariable @NotNull Long departmentIdx) {

        departmentAggregateService.deleteDepartment(requestUser, departmentIdx);

        return ResponseEntity.ok().build();
    }
}
