package com.mtvs.sciencemuseum.domain.auth.controller;

import com.mtvs.sciencemuseum.common.exception.ErrorResponse;
import com.mtvs.sciencemuseum.domain.auth.dto.*;
import com.mtvs.sciencemuseum.domain.auth.service.AuthService;
import com.mtvs.sciencemuseum.domain.user.exception.DuplicatedUsernameException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인이 됬습니다."),
            @ApiResponse(responseCode = "400", description = "비밀번호가 틀렸습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 400, \"errorType\": \"Authentication failed\", \"message\": \"비밀번호가 일치하지 않습니다.\"}"
                    )
            )),
            @ApiResponse(responseCode = "404", description = "회원가입 되지 않은 사용자 입니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"invalid user\", \"message\": \"존재하지 않는 회원입니다.\"}"
                    )
            ))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 됬습니다."),
    })
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequestDTO joinRequestDTO) {
        authService.join(joinRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그인 테스트 API", description = "제대로 로그인이 되서 서버로 토큰이 전송됬는지 확인합니다.")
    @GetMapping("/login-test")
    public ResponseEntity<LoginTestDTO> loginTest(@AuthenticationPrincipal LoginedInfo loginedInfo) {

        LoginTestDTO response = new LoginTestDTO();

        if(loginedInfo == null) {
            response.setResult("로그인 실패!");
            return ResponseEntity.ok().body(response);
        }

        response.setResult("로그인 성공! 사용자 이름: " + loginedInfo.getUsername());
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedUsernameException(DuplicatedUsernameException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorType(e.getErrorCode().getErrorType());
        errorResponse.setStatus(e.getErrorCode().getStatus().value());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }



}
