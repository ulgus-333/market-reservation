package com.reservation.api;

import com.reservation.authentication.domain.annotation.RequireRole;
import com.reservation.authentication.domain.type.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Swagger Example", description = "Example API Docs with swagger-ui")
@RestController
public class presentation {

    @Operation(summary = "Example API 1", description = "Example API 1 description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @RequireRole(Authority.ALL)
    @GetMapping("/example1")
    public ResponseEntity<String> exampleAPI_1() {
        return ResponseEntity.ok("Hello World");
    }

    @Operation(summary = "Example API 2", description = "Example API 2 description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @RequireRole(Authority.ALL)
    @GetMapping("/example2")
    public ResponseEntity<String> exampleAPI_2() {
        return ResponseEntity.ok("Hello World");
    }
}
